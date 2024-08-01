package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.OnUpdateWalkingPlayerEvent;
import com.bt.BThack.impl.Events.PlayerMoveEvent;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityPlayerSP.class, priority = Integer.MAX_VALUE)
public abstract class MixinEntityPlayerSP extends EntityPlayer {

   @Shadow public int positionUpdateTicks;
   @Shadow @Final public NetHandlerPlayClient connection;
   @Shadow public Minecraft mc;
   @Shadow public double lastReportedPosX;
   @Shadow public double lastReportedPosY;
   @Shadow public double lastReportedPosZ;
   @Shadow public float lastReportedYaw;
   @Shadow public float lastReportedPitch;
   @Shadow public boolean serverSprintState;
   @Shadow public boolean serverSneakState;
   @Shadow public boolean prevOnGround;
   @Shadow public boolean autoJumpEnabled;

   public MixinEntityPlayerSP(World worldIn, GameProfile gameProfileIn) {
      super(worldIn, gameProfileIn);
   }


   @Inject(method = {"onLivingUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"), cancellable = true)
   public void closeScreenFixer(CallbackInfo ci) {
      if (Client.getModuleByName("PortalGod").isEnabled()) {
         ci.cancel();
      }
   }

   @Inject(method = {"onLivingUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"), cancellable = true)
   public void openScreenFixer(CallbackInfo ci) {
      if (Client.getModuleByName("PortalGod").isEnabled()) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"move"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void move(MoverType moverType, double x, double y, double z, CallbackInfo callbackInfo) {
      PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(moverType, x, y, z);
      MinecraftForge.EVENT_BUS.post(playerMoveEvent);
      if (playerMoveEvent.isCanceled()) {
         callbackInfo.cancel();
      }

   }

   @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
   private void onUpdateWalkingPlayerHead(CallbackInfo ci) {
      // Setup flags
      Vec3d position = new Vec3d(posX, getEntityBoundingBox().minY, posZ);
      Vec2f rotation = new Vec2f(rotationYaw, rotationPitch);
      boolean moving = isMoving(position);
      boolean rotating = isRotating(rotation);

      OnUpdateWalkingPlayerEvent event = new OnUpdateWalkingPlayerEvent(moving, rotating, position, rotation);
      MinecraftForge.EVENT_BUS.post(event);

      event = event.nextPhase();
      MinecraftForge.EVENT_BUS.post(event);

      if (event.isCanceled()) {
         ci.cancel();

         if (!event.cancelAll) {
            // Copy flags from event
            moving = event.isMoving();
            rotating = event.isRotating();
            position = event.position;
            rotation = event.rotation;

            sendSprintPacket();
            sendSneakPacket();
            sendPlayerPacket(moving, rotating, position, rotation);

            prevOnGround = onGround;
         }

         ++positionUpdateTicks;
         autoJumpEnabled = mc.gameSettings.autoJump;
      }

      event = event.nextPhase();
      MinecraftForge.EVENT_BUS.post(event);
   }

   @Unique
   private void sendSprintPacket() {
      boolean sprinting = isSprinting();

      if (sprinting != serverSprintState) {
         if (sprinting) {
            connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SPRINTING));
         } else {
            connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SPRINTING));
         }
         serverSprintState = sprinting;
      }
   }

   @Unique
   private void sendSneakPacket() {
      boolean sneaking = isSneaking();

      if (sneaking != serverSneakState) {
         if (sneaking) {
            connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SNEAKING));
         } else {
            connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SNEAKING));
         }
         serverSneakState = sneaking;
      }
   }

   @Unique
   private void sendPlayerPacket(boolean moving, boolean rotating, Vec3d position, Vec2f rotation) {
      if (isRiding()) {
         connection.sendPacket(new CPacketPlayer.PositionRotation(motionX, -999.0D, motionZ, rotation.x, rotation.y, onGround));
         moving = false;
      } else if (moving && rotating) {
         connection.sendPacket(new CPacketPlayer.PositionRotation(position.x, position.y, position.z, rotation.x, rotation.y, onGround));
      } else if (moving) {
         connection.sendPacket(new CPacketPlayer.Position(position.x, position.y, position.z, onGround));
      } else if (rotating) {
         connection.sendPacket(new CPacketPlayer.Rotation(rotation.x, rotation.y, onGround));
      } else if (prevOnGround != onGround) {
         connection.sendPacket(new CPacketPlayer(onGround));
      }

      if (moving) positionUpdateTicks = 0;
   }

   @Inject(
      method = {"pushOutOfBlocks(DDD)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable callbackInfoReturnable) {
      if (Client.getModuleByName("Velocity").isEnabled() && Module.getCheckbox("Velocity", "NoPush Blocks")) {
         callbackInfoReturnable.setReturnValue(false);
      }

   }

   private boolean isMoving(Vec3d position) {
      double xDiff = position.x - mc.player.lastReportedPosX;
      double yDiff = position.y - mc.player.lastReportedPosY;
      double zDiff = position.z - mc.player.lastReportedPosZ;

      return positionUpdateTicks >= 20 || xDiff * xDiff + yDiff * yDiff + zDiff * zDiff > 9.0E-4D;
   }

   private boolean isRotating(Vec2f rotation) {
      double yawDiff = rotation.x - mc.player.lastReportedYaw;
      double pitchDiff = rotation.y - mc.player.lastReportedPitch;
      return yawDiff != 0.0D || pitchDiff != 0.0D;
   }
}

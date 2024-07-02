package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PlayerMoveEvent;
import com.bt.BThack.impl.Module.MOVEMENT.Sprint;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityPlayerSP.class, priority = Integer.MAX_VALUE)
public class MixinEntityPlayerSP {
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

   @ModifyArg(method = "setSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;setSprinting(Z)V"), index = 0)
   public boolean modifySprinting(boolean sprinting) {
      if (Client.getModuleByName("Sprint").isEnabled() && Sprint.needSprint()) {
         return true;
      } else {
         return sprinting;
      }
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
}

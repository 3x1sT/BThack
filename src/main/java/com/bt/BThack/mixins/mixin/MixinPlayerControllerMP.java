package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.RightClickBlockEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerControllerMP.class, priority = Integer.MAX_VALUE)
public class MixinPlayerControllerMP {
   @Inject(
      method = {"resetBlockRemoving"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void resetBlockWrapper(CallbackInfo callbackInfo) {
      if (Client.getModuleByName("NoBreakReset").isEnabled()) {
         callbackInfo.cancel();
      }

   }

   @Inject(method = "getBlockReachDistance", at = @At("RETURN"), cancellable = true)
   private void getReachDistanceHook(CallbackInfoReturnable<Float> callbackInfoReturnable) {
      if (Client.getModuleByName("Reach").isEnabled()) {
         callbackInfoReturnable.setReturnValue((float) Module.getSlider("Rich", "Range"));
      }
   }

   @Inject(method = "processRightClickBlock", at = @At("HEAD"), cancellable = true)
   public void modifyRightClickBlock(EntityPlayerSP entityPlayerSP, WorldClient worldClient, BlockPos blockPos, EnumFacing enumFacing, Vec3d vec3d, EnumHand enumHand, CallbackInfoReturnable<EnumActionResult> cir) {
      Event event = new RightClickBlockEvent(entityPlayerSP, worldClient, blockPos, enumFacing, vec3d, enumHand, cir);
      MinecraftForge.EVENT_BUS.post(event);
      if (event.isCanceled()) {
         cir.cancel();
      }
   }
}

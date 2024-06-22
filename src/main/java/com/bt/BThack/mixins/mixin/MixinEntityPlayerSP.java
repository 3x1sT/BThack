package com.bt.BThack.mixins.mixin;

import com.bt.BThack.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PlayerMoveEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.MoverType;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityPlayerSP.class})
public class MixinEntityPlayerSP {
   @Redirect(method = {"onLivingUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
   public void closeScreen(EntityPlayerSP entityPlayerSP) {
      if (Client.getModuleByName("PortalGod").isEnabled()) {
         return;
      }
   }

   @Redirect(method = {"onLivingUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
   public void closeScreen(Minecraft minecraft, GuiScreen guiScreen) {
      if (Client.getModuleByName("PortalGod").isEnabled()) {
         return;
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

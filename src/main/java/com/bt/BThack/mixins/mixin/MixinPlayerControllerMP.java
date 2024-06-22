package com.bt.BThack.mixins.mixin;

import com.bt.BThack.Client;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerControllerMP.class})
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
}

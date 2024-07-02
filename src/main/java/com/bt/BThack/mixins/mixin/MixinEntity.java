package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public class MixinEntity {
   @Inject(
      method = {"applyEntityCollision"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void modifyAcceleration(Entity entity, CallbackInfo callbackInfo) {
      if (Client.getModuleByName("Velocity").isEnabled() && Module.getCheckbox("Velocity", "NoPush Entities")) {
         callbackInfo.cancel();
      }

   }
}

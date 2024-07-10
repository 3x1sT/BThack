package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import net.minecraft.client.audio.ElytraSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraSound.class)
public class MixinElytraSound {
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void update(CallbackInfo ci) {
        if (Client.getModuleByName("ElytraFlight").isEnabled() && !Module.getCheckbox("ElytraFlight", "Elytra Sounds")) {
            ci.cancel();
        }
    }
}
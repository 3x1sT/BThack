package com.bt.BThack.mixins.mixin;

import com.bt.BThack.Client;
import com.bt.BThack.api.Module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public final class MixinWorld {
    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    public void getSkyColor(Entity entity, float partialTicks, CallbackInfoReturnable<Vec3d> callbackInfoReturnable) {
        if (Client.getModuleByName("SkyColour").isEnabled()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(new Vec3d(Module.getSlider("SkyColour", "Red") / 255.0f, Module.getSlider("SkyColour", "Green") / 255.0f, Module.getSlider("SkyColour", "Blue") / 255.0f));
        }
    }
}
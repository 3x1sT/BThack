package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.impl.Module.WORLD.CustomDayTime;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(value = World.class, priority = Integer.MAX_VALUE)
public final class MixinWorld {

    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    public void getSkyColor(Entity entity, float partialTicks, CallbackInfoReturnable<Vec3d> callbackInfoReturnable) {
        if (Client.getModuleByName("SkyColour").isEnabled()) {
            callbackInfoReturnable.cancel();
            callbackInfoReturnable.setReturnValue(new Vec3d(Module.getSlider("SkyColour", "Red") / 255.0f, Module.getSlider("SkyColour", "Green") / 255.0f, Module.getSlider("SkyColour", "Blue") / 255.0f));
        }
    }

    @Inject(method = "getWorldTime", at = @At("HEAD"), cancellable = true)
    public void getWorldTime(CallbackInfoReturnable<Long> cir) {
        if (Client.getModuleByName("CustomDayTime").isEnabled()) {
            cir.setReturnValue(CustomDayTime.time);
        }
    }

    @Inject(method = "getCloudColour", at = @At("HEAD"), cancellable = true)
    public void getCloudColor(float p_getCloudColour_1_, CallbackInfoReturnable<Vec3d> cir) {
        if (Client.getModuleByName("CloudsColor").isEnabled()) {
            cir.setReturnValue(new Vec3d(Module.getSlider("CloudsColor", "Red") / 255, Module.getSlider("CloudsColor", "Green") / 255, Module.getSlider("CloudsColor", "Blue") / 255));
        }
    }

    @Inject(method = "getFogColor", at = @At("HEAD"), cancellable = true)
    public void getFogColor(float p_getFogColor_1_, CallbackInfoReturnable<Vec3d> cir) {
        if (Client.getModuleByName("FogColor").isEnabled()) {
            if (Module.getCheckbox("FogColor", "Rainbow")) {
                Color color = new Color(ColourUtils.rainbowType(2));
                cir.setReturnValue(new Vec3d(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f));
            } else {
                cir.setReturnValue(new Vec3d(Module.getSlider("FogColor", "Red") / 255, Module.getSlider("FogColor", "Green") / 255, Module.getSlider("FogColor", "Blue") / 255));
            }
        }
    }

    @Inject(method = "getStarBrightness", at = @At("HEAD"), cancellable = true)
    public void getStarBrightness(float p_getStarBrightness_1_, CallbackInfoReturnable<Float> cir) {
        if (Client.getModuleByName("WorldElements").isEnabled()) {
            cir.setReturnValue((float) Module.getSlider("WorldElements", "Star Brightness"));
        }
    }

    @Inject(method = "getMoonPhase", at = @At("HEAD"), cancellable = true)
    public void getMoonPhase(CallbackInfoReturnable<Integer> cir) {
        if (Client.isOptionActivated("WorldElements", "Change Moon Phase")) {
            cir.setReturnValue((int) Module.getSlider("WorldElements", "Moon Phase"));
        }
    }
}
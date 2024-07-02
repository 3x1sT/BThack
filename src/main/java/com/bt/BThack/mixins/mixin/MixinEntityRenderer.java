package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.google.common.base.Predicate;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EntityRenderer.class, priority = Integer.MAX_VALUE)
public class MixinEntityRenderer implements Mc {
    @Redirect(method = "getMouseOver", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entity, AxisAlignedBB axisAlignedBB, Predicate predicate) {
        if (Client.getModuleByName("NoEntityTrace").isEnabled() && Module.getCheckbox("NoEntityTrace", "Only Pickaxe") && mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
            return new ArrayList<>();
        } else if (Client.getModuleByName("NoEntityTrace").isEnabled() && !Module.getCheckbox("NoEntityTrace", "Only Pickaxe")) {
            return new ArrayList<>();
        }

        return worldClient.getEntitiesInAABBexcluding(entity, axisAlignedBB, predicate);
    }

    @Inject(method = {"renderRainSnow"}, at = {@At("HEAD")}, cancellable = true)
    public void modifyRenderRainSnow(float p_renderRainSnow_1_, CallbackInfo ci) {
        if (Client.getModuleByName("NoWeather").isEnabled()) {
            ci.cancel();
        }
    }


    @Inject(method = {"addRainParticles"}, at = {@At("HEAD")}, cancellable = true)
    public void modifyRainParticles(CallbackInfo ci) {
        if (Client.getModuleByName("NoWeather").isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"hurtCameraEffect"}, at = {@At("HEAD")}, cancellable = true)
    public void modifyHurtEffect(float p_hurtCameraEffect_1_, CallbackInfo ci) {
        if (Client.getModuleByName("NoOverlay").isEnabled() && Module.getCheckbox("NoOverlay", "Hurt Camera")) {
            ci.cancel();
        }
    }

    @Inject(method = {"setupFog"}, at = {@At("HEAD")}, cancellable = true)
    public void modifyFog(int p_setupFog_1_, float p_setupFog_2_, CallbackInfo ci) {
        if (Client.getModuleByName("NoFog").isEnabled()) {
            ci.cancel();
        }
    }
}

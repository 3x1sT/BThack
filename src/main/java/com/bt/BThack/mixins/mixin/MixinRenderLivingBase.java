package com.bt.BThack.mixins.mixin;

import com.bt.BThack.impl.Events.OnUpdateWalkingPlayerEvent;
import com.bt.BThack.impl.Events.RenderEntityEvent;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderLivingBase.class, priority = 114514)
public class MixinRenderLivingBase<T extends EntityLivingBase> {
    @Inject(method = "renderModel", at = @At("HEAD"))
    public void renderModelHead(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci) {
        if (entity == null || !RenderEntityEvent.isRenderingEntities()) return;

        RenderEntityEvent eventModel = new RenderEntityEvent.Model(entity, OnUpdateWalkingPlayerEvent.Phase.PRE);
        MinecraftForge.EVENT_BUS.post(eventModel);
    }

    @Inject(method = "renderModel", at = @At("RETURN"))
    public void renderEntityReturn(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci) {
        if (entity == null || !RenderEntityEvent.isRenderingEntities()) return;

        RenderEntityEvent eventModel = new RenderEntityEvent.Model(entity, OnUpdateWalkingPlayerEvent.Phase.POST);
        MinecraftForge.EVENT_BUS.post(eventModel);
    }
}
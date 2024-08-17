package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.OnUpdateWalkingPlayerEvent;
import com.bt.BThack.impl.Events.RenderEntityEvent;
import com.bt.BThack.impl.Module.RENDER.Chams.Chams;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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

    @Shadow
    protected ModelBase mainModel;

    @Inject(method = "renderModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void renderModel(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo callbackInfo) {
        if (Client.getModuleByName("Chams").isEnabled() && Chams.entityCheck(entity) && !Module.getMode("Chams", "Mode").equals("Vanilla")) {
            Chams.chamsRender.renderLivingBase(mainModel, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        } else {
            mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        }
    }

    @Inject(method = "doRender", at = @At(value="HEAD"))
    public void doRenderPre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        if (Client.getModuleByName("Chams").isEnabled() && Chams.entityCheck(entity) && Module.getMode("Chams", "Mode").equals("Vanilla")) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }

    @Inject(method = "doRender", at = @At(value="RETURN"))
    public void doRenderPost(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        if (Client.getModuleByName("Chams").isEnabled() && Chams.entityCheck(entity) && Module.getMode("Chams", "Mode").equals("Vanilla")) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }
}
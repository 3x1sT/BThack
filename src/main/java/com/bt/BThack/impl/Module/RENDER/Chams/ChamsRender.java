package com.bt.BThack.impl.Module.RENDER.Chams;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.GL_LIGHTING;

public class ChamsRender {

    public void renderCrystal(ModelBase crystal, ModelBase crystalNoBase, EntityEnderCrystal entityEnderCrystal, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        float rotation = entityEnderCrystal.innerRotation + partialTicks;
        float rotationMoved = MathHelper.sin(rotation * 0.2f) / 2.0f + 0.5f;

        rotationMoved += rotationMoved * rotationMoved;

        GlStateManager.translate(x, y, z);

        GL11.glDisable(GL_LIGHTING);

        RenderUtils.prepareGL();

        GL11.glColor4f((int) Module.getSlider("Chams", "Crystals Red") / 255.0f, (int) Module.getSlider("Chams", "Crystals Green") / 255.0f, (int) Module.getSlider("Chams", "Crystals Blue") / 255.0f, (int) Module.getSlider("Chams", "Crystals Alpha") / 255.0f);

        if (entityEnderCrystal.shouldShowBottom()) {
            crystal.render(entityEnderCrystal, 0.0f, rotation * 3.0f, rotationMoved * 0.2f, 0.0f, 0.0f, 0.0625f);
        } else {
            crystalNoBase.render(entityEnderCrystal, 0.0f, rotation * 3.0f, rotationMoved * 0.2f, 0.0f, 0.0f, 0.0625f);
        }

        RenderUtils.releaseGL();

        GL11.glEnable(GL_LIGHTING);
    }

    public void renderLivingBase(ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GL11.glDisable(GL_LIGHTING);

        RenderUtils.prepareGL();

        GL11.glColor4f(Chams.getChamsColour(entity).getRed() / 255.0f, Chams.getChamsColour(entity).getGreen() / 255.0f, Chams.getChamsColour(entity).getBlue() / 255.0f, Chams.getChamsColour(entity).getAlpha() / 255.0f);

        modelBase.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        RenderUtils.releaseGL();

        GL11.glEnable(GL_LIGHTING);
    }

    public void renderLeftArmPre(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        GL11.glDisable(GL_LIGHTING);

        RenderUtils.prepareGL();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);

        GL11.glColor4f((int) Module.getSlider("Chams", "Left Arm Red") / 255f, (int) Module.getSlider("Chams", "Left Arm Green") / 255f, (int) Module.getSlider("Chams", "Left Arm Blue") / 255f, (int) Module.getSlider("Chams", "Left Arm Alpha") / 255f);
    }

    public void renderLeftArmPost(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        RenderUtils.releaseGL();

        GL11.glEnable(GL_LIGHTING);
    }

    public void renderRightArmPre(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        GL11.glDisable(GL_LIGHTING);

        RenderUtils.prepareGL();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);

        GL11.glColor4f((int) Module.getSlider("Chams", "Right Arm Red") / 255f, (int) Module.getSlider("Chams", "Right Arm Green") / 255f, (int) Module.getSlider("Chams", "Right Arm Blue") / 255f, (int) Module.getSlider("Chams", "Right Arm Alpha") / 255f);
    }

    public void renderRightArmPost(AbstractClientPlayer abstractClientPlayer, CallbackInfo callbackInfo) {
        RenderUtils.releaseGL();

        GL11.glEnable(GL_LIGHTING);
    }
}

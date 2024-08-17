package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;

public class PlayerComponent extends HudComponent {

    public PlayerComponent(Module module) {
        super("PlayerModel",
                new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 5f,
                5,
                false,
                module
        );

        addSlider("Scale", 30, 1, 100, false);

        this.width = 50;
        this.height = 80;
    }

    @Override
    public void render(RenderType type) {
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.color(1, 1, 1 ,1);

        RenderUtils.drawEntityOnScreen( (int) this.getX() + 28, (int) this.getY() + 67, (int) getSlider("Scale"), this.getY() + 13, mc.player);

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }
}

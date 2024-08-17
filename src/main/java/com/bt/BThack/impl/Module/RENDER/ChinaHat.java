package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

public class ChinaHat extends Module {
    public ChinaHat() {
        super("ChinaHat",
                "A Chinese hat appears on your head.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Rainbow", false,"Red", this, 175, 0, 255, true);
        addSlider("Rainbow", false,"Green", this, 175,0,255,true);
        addSlider("Rainbow", false,"Blue", this, 175,0,255,true);

        addCheckbox("Rainbow", this, false);
        addSlider("Rainbow", true,"Rainbow Speed", this, 1, 1, 6, true);

        addCheckbox("OutLine", this, true);
        addSlider("OutLine", true,"Line Width", this, 1,0.1,5, false);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (mc.gameSettings.thirdPersonView != 0 && mc.player.isEntityAlive()) {
            int type = (int) getSlider(this.name, "Rainbow Speed") + 2;

            double ix = -(mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)event.getPartialTicks());
            double iy = -(mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)event.getPartialTicks());
            double iz = -(mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)event.getPartialTicks());
            float x = (float)(mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)event.getPartialTicks());
            float y = (float)(mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)event.getPartialTicks()) + mc.player.height + 0.01F - (mc.player.isSneaking() ? 0.2F : 0.0F);
            float z = (float)(mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)event.getPartialTicks());
            GlStateManager.pushMatrix();
            glDepthMask(false);
            GlStateManager.enableDepth();
            glRotatef(-mc.player.rotationYaw, 0.0F, 1.0F, 0.0F);
            mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 2);
            GlStateManager.translate(ix, iy, iz);
            GlStateManager.enableBlend();
            glBlendFunc(770, 771);
            GlStateManager.disableTexture2D();
            glDisable(2884);
            glShadeModel(7425);
            glDisable(3008);

            glEnable(2848);
            glHint(3154, 4354);

            GlStateManager.alphaFunc(516, 0.0F);
            glBegin(6);
            Color c1 = Module.getCheckbox(this.name, "Rainbow") ? new Color(ColourUtils.rainbowType(type)) : new Color((int)getSlider(this.name, "Red"), (int)getSlider(this.name, "Green"), (int)getSlider(this.name, "Blue"));
            glColor4f((float)c1.getRed() / 255.0F, (float)c1.getGreen() / 255.0F, (float)c1.getBlue() / 255.0F, 0.39215687F);
            glVertex3f(x, y + 0.35F, z);

            for(int i = 0; i <= 360; ++i) {
                double x1 = Math.cos((double)i * 3.141592653589793D / 180.0D) * 0.66D;
                double z1 = Math.sin((double)i * 3.141592653589793D / 180.0D) * 0.66D;
                Color c = Module.getCheckbox(this.name, "Rainbow") ? new Color(ColourUtils.rainbowType(type)) : new Color((int)getSlider(this.name, "Red"), (int)getSlider(this.name, "Green"), (int)getSlider(this.name, "Blue"));
                glColor4f((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, 0.39215687F);
                glVertex3d((double)x + x1, y, (double)z + z1);
            }

            glEnd();
            if (getCheckbox(this.name, "OutLine")) {
                glLineWidth((float)getSlider(this.name, "Line Width"));
                glBegin(2);

                for(int i = 0; i <= 360; ++i) {
                    double x1 = Math.cos((double)i * 3.141592653589793D / 180.0D) * 0.66D;
                    double z1 = Math.sin((double)i * 3.141592653589793D / 180.0D) * 0.66D;
                    Color c = Module.getCheckbox(this.name, "Rainbow") ? new Color(ColourUtils.rainbowType(type)) : new Color((int)getSlider(this.name, "Red"), (int)getSlider(this.name, "Green"), (int)getSlider(this.name, "Blue"));
                    glColor4f((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, 1.0F);
                    glVertex3d((double)x + x1, y, (double)z + z1);
                }

                glEnd();
            }

            glHint(3154, 4352);
            glDisable(2848);

            glEnable(3008);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            glEnable(2884);
            mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
            GlStateManager.resetColor();
            glDepthMask(true);
            GlStateManager.popMatrix();
        }
    }
}

package com.bt.BThack.api.Utils.Render;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static org.lwjgl.opengl.GL11.*;
import net.minecraft.client.renderer.GlStateManager;

public class RenderUtils implements Mc {
    public static void trace(Entity e, float partialTicks, double Red, double Green, double Blue, double Alpha) {
        if (mc.getRenderManager().renderViewEntity != null) {
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_LIGHTING);
            glLineWidth(2F);

            glPushMatrix();
            glDepthMask(false);
            glColor4d(Red, Green, Blue, Alpha);

            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glDisable(GL_TEXTURE_2D);
            glBegin(GL_LINES);

            RenderManager r = mc.getRenderManager();

            Vec3d v = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-((float) Math.toRadians(mc.player.rotationPitch))).rotateYaw(-((float) Math.toRadians(mc.player.rotationYaw)));

            glVertex3d(v.x, mc.player.getEyeHeight() + v.y, v.z);

            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * partialTicks;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * partialTicks;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * partialTicks;

            glVertex3d(x - r.viewerPosX, y - r.viewerPosY + 0.25, z - r.viewerPosZ);

            glEnd();
            glDepthMask(true);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_TEXTURE_2D);
            glPopMatrix();
        }
    }

    public static void FillLine(AxisAlignedBB box, float LinesRed, float LinesGreen, float LinesBlue, float LinesAlpha, float BoxRed, float BoxGreen, float BoxBlue, float BoxAlpha) {
        glBlendFunc(770, 771);
        glEnable(GL_BLEND);
        glLineWidth(2.0F);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);

        RenderGlobal.renderFilledBox(box, BoxRed, BoxGreen, BoxBlue, BoxAlpha);
        RenderGlobal.drawSelectionBoundingBox(box, LinesRed, LinesGreen, LinesBlue, LinesAlpha);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public static void ChestESP(BlockPos blockPos, float LinesRed, float LinesGreen, float LinesBlue, float LinesAlpha, float BoxRed, float BoxGreen, float BoxBlue, float BoxAlpha) {
        glPushMatrix();

        double x =
                blockPos.getX()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double y =
                blockPos.getY()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double z =
                blockPos.getZ()
                        - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

        glBlendFunc(770,771);
        glEnable(GL_BLEND);

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);

        glDepthMask(false);

        RenderGlobal.renderFilledBox(new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1), BoxRed, BoxGreen, BoxBlue, BoxAlpha);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1), LinesRed, LinesGreen, LinesBlue, LinesAlpha);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        glDepthMask(true);
        glDisable(GL_BLEND);
        glPopMatrix();
    }

    public static void drawOutlineRect(int x1, int y1, int x2, int y2, int depth, int colour) {
        int outlineX;
        int outlineY;
        outlineX = x1 > x2 ? -depth : depth;
        outlineY = y1 > y2 ? depth : -depth;

        Gui.drawRect(x1,y1, x1 + outlineX, y2, colour);
        Gui.drawRect(x1 + outlineX, y2, x2, y2 + outlineY, colour);
        Gui.drawRect(x2, y2 + outlineY, x2 - outlineX, y1, colour);
        Gui.drawRect(x2 - outlineX, y1, x1, y1 - outlineY, colour);
    }

    public static void drawGradientRainbowRect(int x1, int y1, int x2, int y2, int rainbowType) {
        final float[] counter = {1};
        int dX;
        int tX = x1;
        int delay = (int)RainbowUtils.getRainbowRectSpeed(rainbowType)[1];
        float speed = RainbowUtils.getRainbowRectSpeed(rainbowType)[0];

        float fX;

        if (x1 < x2) {
            fX = x2 - x1;
            fX /= 45;
        } else {
            fX = x1 - x2;
            fX = -(fX / 45);
        }
        dX = fX != 0 ? (int) Math.ceil(fX) : 0;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        while (tX != x2) {

            if (x1 < x2) {
                if (tX + dX > x2) {
                    dX = x2 - tX;
                }
            } else {
                if (tX + dX < x2) {
                    dX = tX - x2;
                }
            }

            drawNoGL11Rect(tX, y1, tX + dX, y2, ColourUtils.rainbow((int)(counter[0] * delay), speed), bufferbuilder, tessellator);
            tX += dX;
            counter[0]++;
        }

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawNoGL11Rect(int x1, int y1, int x2, int y2, int color, BufferBuilder builder, Tessellator tessellator)
    {
        if (x1 < x2)
        {
            int i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2)
        {
            int j = y1;
            y1 = y2;
            y2 = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        GlStateManager.color(f, f1, f2, f3);
        builder.begin(7, DefaultVertexFormats.POSITION);
        builder.pos(x1, y2, 0.0D).endVertex();
        builder.pos(x2, y2, 0.0D).endVertex();
        builder.pos(x2, y1, 0.0D).endVertex();
        builder.pos(x1, y1, 0.0D).endVertex();
        tessellator.draw();
    }

    public static void drawSquare(int x, int y, int size, int color) {
        Gui.drawRect(x - size, y - size, x + size, y + size, color);
    }

    public static void drawTriangle(float x, float y, float size, float theta, int color)
    {
        glTranslated(x, y, 0);
        glRotatef(180 + theta, 0F, 0F, 1.0F);

        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;

        glColor4f(red, green, blue, alpha);
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        glBlendFunc(770, 771);
        glLineWidth(1);
        glBegin(GL_TRIANGLE_FAN);

        glVertex2d(0, (1.0F * size));
        glVertex2d((1 * size), -(1.0F * size));
        glVertex2d(-(1 * size), -(1.0F * size));

        glEnd();
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glRotatef(-180 - theta, 0F, 0F, 1.0F);
        glTranslated(-x, -y, 0);
    }

    public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, int color)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.025F, -0.025F, 0.025F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.disableDepth();

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int i = fontRendererIn.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((-i - 1), (-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((-i - 1), (8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((i + 1), (8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        bufferbuilder.pos((i + 1), (-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();

        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, 553648127);
        GlStateManager.enableDepth();

        GlStateManager.depthMask(true);
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, color);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}

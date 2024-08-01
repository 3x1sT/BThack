package com.bt.BThack.api.Utils.Render;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

public class RenderUtils implements Mc {
    private static Tessellator tessellator;
    private static BufferBuilder bufferBuilder;

    public static void init() {
        tessellator = Tessellator.getInstance();
        bufferBuilder = tessellator.getBuffer();
    }

    public static void prepareGL() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        GL11.glEnable(GL_LINE_SMOOTH);
        GL11.glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
    }

    public static void releaseGL() {
        GL11.glDisable(GL_LINE_SMOOTH);

        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void trace(Entity e, float partialTicks, double Red, double Green, double Blue, double Alpha) {
        if (mc.getRenderManager().renderViewEntity != null) {
            glPushMatrix();

            glDisable(GL_DEPTH_TEST);
            glDisable(GL_LIGHTING);
            glLineWidth(2F);

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

            glColor4f(1f,1f,1f,1f);

            glPopMatrix();
        }
    }

    public static void trace(TileEntity e, double Red, double Green, double Blue, double Alpha) {
        if (mc.getRenderManager().renderViewEntity != null) {
            glPushMatrix();

            glDisable(GL_DEPTH_TEST);
            glDisable(GL_LIGHTING);
            glLineWidth(2F);
            glDepthMask(false);
            glColor4d(Red, Green, Blue, Alpha);

            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glDisable(GL_TEXTURE_2D);
            glBegin(GL_LINES);

            RenderManager r = mc.getRenderManager();

            Vec3d v = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-((float) Math.toRadians(mc.player.rotationPitch))).rotateYaw(-((float) Math.toRadians(mc.player.rotationYaw)));

            glVertex3d(v.x, mc.player.getEyeHeight() + v.y, v.z);

            BlockPos pos = e.getPos();

            double x = pos.x;
            double y = pos.y;
            double z = pos.z;

            glVertex3d(x - r.viewerPosX, y - r.viewerPosY + 0.25, z - r.viewerPosZ);

            glEnd();
            glDepthMask(true);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_TEXTURE_2D);

            glColor4f(1f,1f,1f,1f);

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

    public static void drawBox(BlockPos pos, Color linesColor, Color boxColor) {
        drawBox(pos, linesColor.getRed() / 255f, linesColor.getGreen() / 255f, linesColor.getBlue() / 255f, linesColor.getAlpha() / 255f, boxColor.getRed() / 255f, boxColor.getGreen() / 255f, boxColor.getBlue() / 255f, boxColor.getAlpha() / 255f);
    }

    public static void drawBox(BlockPos pos, double height, Color linesColor, Color boxColor) {
        drawBox(pos, height, linesColor.getRed() / 255f, linesColor.getGreen() / 255f, linesColor.getBlue() / 255f, linesColor.getAlpha() / 255f, boxColor.getRed() / 255f, boxColor.getGreen() / 255f, boxColor.getBlue() / 255f, boxColor.getAlpha() / 255f);
    }

    public static void drawBox(BlockPos blockPos, float linesRed, float linesGreen, float linesBlue, float linesAlpha, float boxRed, float boxGreen, float boxBlue, float boxAlpha) {
        drawBox(blockPos, 1, linesRed, linesGreen, linesBlue, linesAlpha, boxRed, boxGreen, boxBlue, boxAlpha);
    }


    public static void drawBox(BlockPos blockPos, double height, float LinesRed, float LinesGreen, float LinesBlue, float LinesAlpha, float BoxRed, float BoxGreen, float BoxBlue, float BoxAlpha) {
        glPushMatrix();

        double x =
                blockPos.getX()
                        - mc.getRenderManager().viewerPosX;
        double y =
                blockPos.getY()
                        - mc.getRenderManager().viewerPosY;
        double z =
                blockPos.getZ()
                        - mc.getRenderManager().viewerPosZ;

        glBlendFunc(770,771);
        glEnable(GL_BLEND);

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);

        glDepthMask(false);

        RenderGlobal.renderFilledBox(new AxisAlignedBB(x, y, z, x + 1, y + height, z + 1), BoxRed, BoxGreen, BoxBlue, BoxAlpha);
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1, y + height, z + 1), LinesRed, LinesGreen, LinesBlue, LinesAlpha);

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

    public static void drawVerticalGradientRect(int x1, int y1, int x2, int y2, int startColor, int endColor) {
        float startRed = (float)(startColor >> 16 & 255) / 255.0F;
        float startGreen = (float)(startColor >> 8 & 255) / 255.0F;
        float startBlue = (float)(startColor & 255) / 255.0F;
        float startAlpha = (float)(startColor >> 24 & 255) / 255.0F;

        float endRed = (float)(endColor >> 16 & 255) / 255.0F;
        float endGreen = (float)(endColor >> 8 & 255) / 255.0F;
        float endBlue = (float)(endColor & 255) / 255.0F;
        float endAlpha = (float)(endColor >> 24 & 255) / 255.0F;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);

        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        //right up
        bufferBuilder.pos(x2, y1, 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        //left up
        bufferBuilder.pos(x1, y1, 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        //left down
        bufferBuilder.pos(x1, y2, 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        //right down
        bufferBuilder.pos(x2, y2, 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();

        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawHorizontalGradientRect(int x1, int y1, int x2, int y2, int startColor, int endColor) {
        float startRed = (float)(startColor >> 16 & 255) / 255.0F;
        float startGreen = (float)(startColor >> 8 & 255) / 255.0F;
        float startBlue = (float)(startColor & 255) / 255.0F;
        float startAlpha = (float)(startColor >> 24 & 255) / 255.0F;

        float endRed = (float)(endColor >> 16 & 255) / 255.0F;
        float endGreen = (float)(endColor >> 8 & 255) / 255.0F;
        float endBlue = (float)(endColor & 255) / 255.0F;
        float endAlpha = (float)(endColor >> 24 & 255) / 255.0F;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);

        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        //right up
        bufferBuilder.pos(x2, y1, 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        //left up
        bufferBuilder.pos(x1, y1, 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        //left down
        bufferBuilder.pos(x1, y2, 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        //right down
        bufferBuilder.pos(x2, y2, 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();

        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void draw4ColorRect(int x1, int y1, int x2, int y2, int x1y1Color, int x2y1Color, int x1y2Color, int x2y2Color) {
        float x1y1Red = (float)(x1y1Color >> 16 & 255) / 255.0F;
        float x1y1Green = (float)(x1y1Color >> 8 & 255) / 255.0F;
        float x1y1Blue = (float)(x1y1Color & 255) / 255.0F;
        float x1y1Alpha = (float)(x1y1Color >> 24 & 255) / 255.0F;

        float x2y1Red = (float)(x2y1Color >> 16 & 255) / 255.0F;
        float x2y1Green = (float)(x2y1Color >> 8 & 255) / 255.0F;
        float x2y1Blue = (float)(x2y1Color & 255) / 255.0F;
        float x2y1Alpha = (float)(x2y1Color >> 24 & 255) / 255.0F;

        float x1y2Red = (float)(x1y2Color >> 16 & 255) / 255.0F;
        float x1y2Green = (float)(x1y2Color >> 8 & 255) / 255.0F;
        float x1y2Blue = (float)(x1y2Color & 255) / 255.0F;
        float x1y2Alpha = (float)(x1y2Color >> 24 & 255) / 255.0F;

        float x2y2Red = (float)(x2y2Color >> 16 & 255) / 255.0F;
        float x2y2Green = (float)(x2y2Color >> 8 & 255) / 255.0F;
        float x2y2Blue = (float)(x2y2Color & 255) / 255.0F;
        float x2y2Alpha = (float)(x2y2Color >> 24 & 255) / 255.0F;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);

        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        //right up
        bufferBuilder.pos(x2, y1, 0).color(x2y1Red, x2y1Green, x2y1Blue, x2y1Alpha).endVertex();
        //left up
        bufferBuilder.pos(x1, y1, 0).color(x1y1Red, x1y1Green, x1y1Blue, x1y1Alpha).endVertex();
        //left down
        bufferBuilder.pos(x1, y2, 0).color(x1y2Red, x1y2Green, x1y2Blue, x1y2Alpha).endVertex();
        //right down
        bufferBuilder.pos(x2, y2, 0).color(x2y2Red, x2y2Green, x2y2Blue, x2y2Alpha).endVertex();
        tessellator.draw();

        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
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

        //GlStateManager.enableBlend();
        //GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int i = fontRendererIn.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        float speed = RainbowUtils.getRainbowRectSpeed(2)[0];
        int delay = (int) RainbowUtils.getRainbowRectSpeed(2)[1];
        Color rainbowColor = new Color(ColourUtils.rainbow(delay, speed));
        GlStateManager.color(rainbowColor.getRed() / 255f, rainbowColor.getGreen() / 255f, rainbowColor.getBlue() / 255f);
        bufferbuilder.pos((-i - 3.5), (-3.5 + verticalShift), 0.03D).endVertex();
        bufferbuilder.pos((-i - 3.5), (11.5 + verticalShift), 0.03D).endVertex();
        bufferbuilder.pos((i + 3.5), (11.5 + verticalShift), 0.03D).endVertex();
        bufferbuilder.pos((i + 3.5), (-3.5 + verticalShift), 0.03D).endVertex();
        tessellator.draw();


        GlStateManager.color(0f,0f,0f,1f);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        //GlStateManager.color(0f,0f,0f,1f);
        bufferbuilder.pos((-i - 2.5), (-2.5 + verticalShift), 0.02D).endVertex();
        //GlStateManager.color(1f,1f,1f,1f);
        bufferbuilder.pos((-i - 2.5), (10.5 + verticalShift), 0.02D).endVertex();
        //GlStateManager.color(1f,1f,1f,1f);
        bufferbuilder.pos((i + 2.5), (10.5 + verticalShift), 0.02D).endVertex();
        //GlStateManager.color(0f,0f,0f,1f);
        bufferbuilder.pos((i + 2.5), (-2.5 + verticalShift), 0.02D).endVertex();
        tessellator.draw();


        GlStateManager.enableTexture2D();

        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, 553648127);
        GlStateManager.enableDepth();

        GlStateManager.depthMask(true);
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, color);
        GlStateManager.enableLighting();
        //GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public static void drawScaledCustomSizeModalRect(double x, double y, double u, double v, double uWidth, double vHeight, double width, double height, double tileWidth, double tileHeight) {
        double f = 1.0F / tileWidth;
        double f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0).tex(u * f, (v + (float)vHeight) * f1).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0).tex((u + (float)uWidth) * f, (v + (float)vHeight) * f1).endVertex();
        bufferbuilder.pos(x + width, y, 0.0).tex((u + (float)uWidth) * f, v * f1).endVertex();
        bufferbuilder.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();

        tessellator.draw();
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseY, EntityLivingBase entityLivingBase) {
        RenderManager renderManager = mc.getRenderManager();

        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0f);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);

        RenderHelper.enableStandardItemLighting();

        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, 0.0f);

        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow(false);
        renderManager.renderEntity(entityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        renderManager.setRenderShadow(true);

        GlStateManager.popMatrix();

        RenderHelper.disableStandardItemLighting();

        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}

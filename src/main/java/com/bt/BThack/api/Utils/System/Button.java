package com.bt.BThack.api.Utils.System;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Button implements Mc {

    private final int id;

    private int centerX;
    private int centerY;

    private int width;
    private int height;

    private ResourceLocation texture = null;

    private String text;

    private boolean hovered;

    private float halfTextWidth;
    private float halfTextHeight;



    public Button(int id, int x, int y, int width, int height, String text) {
        this.id = id;

        this.centerX = x;
        this.centerY = y;

        this.width = width;
        this.height = height;

        this.text = text;

        this.halfTextWidth = FontUtil.getStringWidth(text) / 2f;
        this.halfTextHeight = FontUtil.getStringHeight(text) / 2f;
    }

    public Button(int id, int x, int y, int width, int height, String text, ResourceLocation texture) {
        this.id = id;

        this.centerX = x;
        this.centerY = y;

        this.width = width;
        this.height = height;

        this.text = text;

        this.texture = texture;

        this.halfTextWidth = FontUtil.getStringWidth(text) / 2f;
        this.halfTextHeight = FontUtil.getStringHeight(text) / 2f;
    }

    public void updateButton(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
    }


    private int rectColor = new Color(0, 0, 0, 76).hashCode();

    private int whiteColor = new Color(255,255,255, 178).hashCode();
    private int alphaColor = new Color(255,255,255,0).hashCode();
    public void renderButton() {
        if (!this.hovered) {
            if (texture != null) {
                GlStateManager.pushMatrix();
                mc.renderEngine.bindTexture(this.texture);
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                Gui.drawScaledCustomSizeModalRect(this.centerX - this.width, this.centerY - this.height, 0, 0, this.width * 2, this.height * 2, this.width * 2, this.height * 2, this.width * 2, this.height * 2);
                glDisable(GL_BLEND);
                GL11.glBindTexture(GL_TEXTURE_2D, 0);
                GlStateManager.popMatrix();
            } else {
                Gui.drawRect(this.centerX - this.width, this.centerY - this.height, this.centerX + this.width, this.centerY + this.height, rectColor);

                FontUtil.drawTextNoShadow(this.text, this.centerX - this.halfTextWidth, this.centerY - this.halfTextHeight, -1);
            }
        } else {
            if (texture != null) {
                GlStateManager.pushMatrix();
                mc.renderEngine.bindTexture(this.texture);
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                Gui.drawScaledCustomSizeModalRect(this.centerX - this.width - 1, this.centerY - this.height - 1, 0, 0, (this.width * 2) + 2, (this.height * 2) + 2, (this.width * 2) + 2, (this.height * 2) + 2, (this.width * 2) + 2, (this.height * 2) + 2);
                glDisable(GL_BLEND);
                GL11.glBindTexture(GL_TEXTURE_2D, 0);
                GlStateManager.popMatrix();
            } else {
                Gui.drawRect(this.centerX - this.width - 1, this.centerY - this.height - 1, this.centerX + this.width + 1, this.centerY + this.height + 1, rectColor);

                RenderUtils.drawHorizontalGradientRect((int)(this.centerX - (this.width * 0.8)), this.centerY + this.height - 4, this.centerX, this.centerY + this.height - 2, alphaColor, whiteColor);
                RenderUtils.drawHorizontalGradientRect(this.centerX, this.centerY + this.height - 4, (int)(this.centerX + (this.width * 0.8)), this.centerY + this.height - 2, whiteColor, alphaColor);
                FontUtil.drawTextNoShadow(this.text, this.centerX - this.halfTextWidth, this.centerY - this.halfTextHeight, -1);
            }
        }
    }



    public boolean isMouseOnButton(int mouseX, int mouseY) {
        return centerX - width <= mouseX && mouseX <= centerX + width && centerY - height <= mouseY && mouseY <= centerY + height;
    }


    public int getId() {
        return this.id;
    }

    public int getCenterX() {
        return this.centerX;
    }

    public int getCenterY() {
        return this.centerY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getText() {
        return this.text;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setText(String text) {
        this.text = text;

        this.halfTextWidth = FontUtil.getStringWidth(text);
        this.halfTextHeight = FontUtil.getStringHeight(text) / 2f;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }
}

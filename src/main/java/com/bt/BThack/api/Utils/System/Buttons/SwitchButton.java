package com.bt.BThack.api.Utils.System.Buttons;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class SwitchButton extends Button {
    private boolean bVal;

    public SwitchButton(int id,int centerX, int centerY, int width, int height, boolean defaultBVal, String text) {
        super(id, centerX, centerY, width, height, text);

        this.bVal = defaultBVal;
    }


    @Override
    public void renderButton() {
        if (!this.hovered) {
            if (getTexture() != null) {
                GlStateManager.pushMatrix();
                mc.renderEngine.bindTexture(this.getTexture());
                Gui.drawScaledCustomSizeModalRect(this.getCenterX() - this.getWidth(), this.getCenterY() - this.getHeight(), 0, 0, this.getWidth() * 2, this.getHeight() * 2, this.getWidth() * 2, this.getHeight() * 2, this.getWidth() * 2, this.getHeight() * 2);
                GL11.glBindTexture(GL_TEXTURE_2D, 0);
                GlStateManager.popMatrix();
            } else {
                Gui.drawRect(this.getCenterX() - this.getWidth(), this.getCenterY() - this.getHeight(), this.getCenterX() + this.getWidth(), this.getCenterY() + this.getHeight(), rectColor);

                FontUtil.drawTextNoShadow(this.getText(), this.getCenterX() - this.getHalfTextWidth(), this.getCenterY() - (FontUtil.getStringHeight(this.text) / 2), -1);
            }
        } else {
            if (getTexture() != null) {
                GlStateManager.pushMatrix();
                mc.renderEngine.bindTexture(this.getTexture());
                Gui.drawScaledCustomSizeModalRect(this.getCenterX() - this.getWidth() - 1, this.getCenterY() - this.getHeight() - 1, 0, 0, (this.getWidth() * 2) + 2, (this.getHeight() * 2) + 2, (this.getWidth() * 2) + 2, (this.getHeight() * 2) + 2, (this.getWidth() * 2) + 2, (this.getHeight() * 2) + 2);
                GL11.glBindTexture(GL_TEXTURE_2D, 0);
                GlStateManager.popMatrix();
            } else {
                Gui.drawRect(this.getCenterX() - this.getWidth() - 1, this.getCenterY() - this.getHeight() - 1, this.getCenterX() + this.getWidth() + 1, this.getCenterY() + this.getHeight() + 1, rectColor);

                RenderUtils.drawHorizontalGradientRect((int)(this.getCenterX() - (this.getWidth() * 0.8)), this.getCenterY() + this.getHeight() - 4, this.getCenterX(), this.getCenterY() + this.getHeight() - 2, alphaColor, whiteColor);
                RenderUtils.drawHorizontalGradientRect(this.getCenterX(), this.getCenterY() + this.getHeight() - 4, (int)(this.getCenterX() + (this.getWidth() * 0.8)), this.getCenterY() + this.getHeight() - 2, whiteColor, alphaColor);
                FontUtil.drawTextNoShadow(this.getText(), this.getCenterX() - this.getHalfTextWidth(), this.getCenterY() - (FontUtil.getStringHeight(this.text) / 2), -1);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        if (this.isMouseOnButton(mouseX, mouseY)) {
            this.bVal = !this.bVal;
        }
    }

    @Override
    public String getText() {
        return this.text + ": " + (this.bVal ? "ON" : "OFF");
    }

    public float getHalfTextWidth() {
        return (FontUtil.getStringWidth(this.getText()) / 2);
    }

    public boolean getBVal() {
        return this.bVal;
    }
}

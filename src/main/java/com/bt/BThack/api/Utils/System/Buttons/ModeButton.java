package com.bt.BThack.api.Utils.System.Buttons;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class ModeButton extends Button {
    private final ArrayList<String> options;

    int index = 0;
    String sVal;

    public ModeButton(int id, int centerX, int centerY, int width, int height, String text, ArrayList<String> options) {
        super(id, centerX, centerY, width, height, text);

        this.options = options;

        this.sVal = this.options.get(index);
    }

    @Override
    public void renderButton() {
        if (!this.hovered) {
            Gui.drawRect(this.getCenterX() - this.getWidth(), this.getCenterY() - this.getHeight(), this.getCenterX() + this.getWidth(), this.getCenterY() + this.getHeight(), rectColor);

            FontUtil.drawTextNoShadow(this.getText(), this.getCenterX() - this.getHalfTextWidth(), this.getCenterY() - (FontUtil.getStringHeight(this.text) / 2), -1);
        } else {
            Gui.drawRect(this.getCenterX() - this.getWidth() - 1, this.getCenterY() - this.getHeight() - 1, this.getCenterX() + this.getWidth() + 1, this.getCenterY() + this.getHeight() + 1, rectColor);

            RenderUtils.drawHorizontalGradientRect((int)(this.getCenterX() - (this.getWidth() * 0.8)), this.getCenterY() + this.getHeight() - 4, this.getCenterX(), this.getCenterY() + this.getHeight() - 2, alphaColor, whiteColor);
            RenderUtils.drawHorizontalGradientRect(this.getCenterX(), this.getCenterY() + this.getHeight() - 4, (int)(this.getCenterX() + (this.getWidth() * 0.8)), this.getCenterY() + this.getHeight() - 2, whiteColor, alphaColor);
            FontUtil.drawTextNoShadow(this.getText(), this.getCenterX() - this.getHalfTextWidth(), this.getCenterY() - (FontUtil.getStringHeight(this.text) / 2), -1);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        super.mouseClicked(mouseX, mouseY);

        if (!isMouseOnButton(mouseX, mouseY)) return;

        if (index >= (options.size() - 1))
            index = 0;
        else
            index++;

        this.sVal = options.get(index);
    }

    @Override
    public String getText() {
        return this.text + ": " + this.sVal;
    }


    public String getsVal() {
        return this.sVal;
    }

    public float getHalfTextWidth() {
        return (FontUtil.getStringWidth(this.getText()) / 2);
    }
}

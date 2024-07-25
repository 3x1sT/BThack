package com.bt.BThack.api.Utils.System.Buttons;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.google.common.collect.Sets;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Set;

public class NumberFrameButton extends Button {
    private final StringBuilder textBuilder = new StringBuilder();

    private boolean selected = false;

    private final Set<Integer> keys = Sets.newHashSet(
            Keyboard.KEY_0,
            Keyboard.KEY_1,
            Keyboard.KEY_2,
            Keyboard.KEY_3,
            Keyboard.KEY_4,
            Keyboard.KEY_5,
            Keyboard.KEY_6,
            Keyboard.KEY_7,
            Keyboard.KEY_8,
            Keyboard.KEY_9
    );


    public NumberFrameButton(int id, int centerX, int centerY, int width, int height) {
        super(id,centerX,centerY, width, height, "");
    }


    @Override
    public void renderButton() {
        Gui.drawRect(this.getCenterX() - this.getWidth(), this.getCenterY() - this.getHeight(), this.getCenterX() + this.getWidth(), this.getCenterY() + this.getHeight(), rectColor);

        FontUtil.drawText(textBuilder.toString(), this.getCenterX() - this.getWidth() + 3, this.getCenterY() - (FontUtil.getStringHeight(this.getText()) / 2), Color.white.hashCode());
    }

    @Override
    public void keyTyped(int key, char charKey) {
        super.keyTyped(key, charKey);

        if (!this.selected) return;

        if (key == Keyboard.KEY_MINUS) {
            if (textBuilder.length() == 0)
                textBuilder.append(charKey);
        }

        if (keys.contains(key) || charKey == '.') {
            if (FontUtil.getStringWidth(textBuilder.toString()) < ((this.getWidth() * 2) - ((this.getWidth() * 2) * 0.1))) {
                textBuilder.append(charKey);
            }
        }
        if (key == 14) {
            if (textBuilder.length() > 0)
                textBuilder.deleteCharAt(textBuilder.length() - 1);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        this.selected = this.isMouseOnButton(mouseX, mouseY);
    }

    @Override
    public String getText() {
        return textBuilder.toString();
    }

    public double getNumber() {
        if (textBuilder.length() > 0) {
            if (textBuilder.charAt(textBuilder.length() - 1) == '.') {
                textBuilder.append("0");
            }
        }
        if (textBuilder.length() == 0) {
            return 0;
        }

        return Double.parseDouble(textBuilder.toString());
    }
}

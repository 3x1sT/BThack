package com.bt.BThack.api.Utils.System.Buttons;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.google.common.collect.Sets;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Set;

public class TextFrameButton extends Button {
    private final StringBuilder textBuilder = new StringBuilder();

    private final Set<Integer> ignoreKeys = Sets.newHashSet(
            Keyboard.KEY_RSHIFT,
            Keyboard.KEY_DELETE,
            Keyboard.KEY_UP,
            Keyboard.KEY_LEFT,
            Keyboard.KEY_DOWN,
            Keyboard.KEY_RIGHT,
            Keyboard.KEY_TAB,
            Keyboard.KEY_ESCAPE,
            Keyboard.KEY_F1,
            Keyboard.KEY_F2,
            Keyboard.KEY_F3,
            Keyboard.KEY_F4,
            Keyboard.KEY_F5,
            Keyboard.KEY_F6,
            Keyboard.KEY_F7,
            Keyboard.KEY_F8,
            Keyboard.KEY_F9,
            Keyboard.KEY_F10,
            Keyboard.KEY_F11,
            Keyboard.KEY_F12,
            Keyboard.KEY_F13,
            Keyboard.KEY_F14,
            Keyboard.KEY_F15,
            Keyboard.KEY_F16,
            Keyboard.KEY_F17,
            Keyboard.KEY_F18,
            Keyboard.KEY_F19,
            Keyboard.KEY_NUMLOCK,
            Keyboard.KEY_HOME,
            Keyboard.KEY_END,
            Keyboard.KEY_PAUSE,
            Keyboard.KEY_INSERT,
            14,
            42,
            58,
            29,
            56,
            184,
            157,
            201,
            209,
            28
    );

    private boolean selected = false;


    public TextFrameButton(int id, int centerX, int centerY, int width, int height) {
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

        if (key == 14) {
            if (textBuilder.length() > 0)
                textBuilder.deleteCharAt(textBuilder.length() - 1);
        }

        if (ignoreKeys.contains(key))
            return;

        if (FontUtil.getStringWidth(textBuilder.toString()) < ((this.getWidth() * 2) - ((this.getWidth() * 2) * 0.1))) {
            textBuilder.append(charKey);
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
}

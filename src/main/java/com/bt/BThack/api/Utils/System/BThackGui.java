package com.bt.BThack.api.Utils.System;

import com.bt.BThack.api.Storage.MusicStorage;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.SoundCategory;

import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class BThackGui extends GuiScreen {
    public ArrayList<Button> buttons = new ArrayList<>();

    public Button activeButton = new Button(Integer.MIN_VALUE, -100, -100, 1, 1, "nullButton");


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        for (Button button : buttons) {
            button.updateButton(mouseX, mouseY);

            button.renderButton();
        }
        glDisable(GL_BLEND);
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (Button button : buttons) {
            button.mouseClicked(mouseX, mouseY);

            if (button.isMouseOnButton(mouseX, mouseY)) {
                activeButton = button;
                MusicStorage.buttonClicked.play(mc.gameSettings.getSoundLevel(SoundCategory.MASTER) / 2);
            }
        }
    }

    @Override
    protected void keyTyped(char charKey, int keyCode) throws IOException {
        super.keyTyped(charKey, keyCode);

        for (Button button : buttons) {
            button.keyTyped(keyCode, charKey);
        }
    }

    public Button getButtonFromId(int id) {
        for (Button button : buttons) {
            if (button.getId() == id)
                return button;
        }
        return null;
    }
}

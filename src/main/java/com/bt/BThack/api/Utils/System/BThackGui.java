package com.bt.BThack.api.Utils.System;

import com.bt.BThack.api.Storage.MusicStorage;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public class BThackGui extends GuiScreen {
    public ArrayList<Button> buttons = new ArrayList<>();

    public Button activeButton = new Button(Integer.MIN_VALUE, -100, -100, 1, 1, "nullButton");


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (Button button : buttons) {
            button.updateButton(mouseX, mouseY);

            button.renderButton();
        }
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (Button button : buttons) {
            if (button.isMouseOnButton(mouseX, mouseY)) {
                activeButton = button;
                MusicStorage.buttonClicked.play();
            }
        }
    }
}

package com.bt.BThack.impl.CustomGui.Gui;

import com.bt.BThack.api.Social.Enemies.EnemiesUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class EnemiesGui extends GuiScreen {
    GuiTextField inputField;

    @Override
    public void initGui() {
        int i = this.height / 4 + 48;

        this.buttonList.clear();
        inputField = new GuiTextField(1, fontRenderer, this.width / 2 - 100, i + 60, 200, 20);

        inputField.setText("Enemy");

        this.buttonList.add(new GuiButton(1, width / 2 - 100, i + 84, 98, 20, "Add Enemy"));
        this.buttonList.add(new GuiButton(2, width / 2 + 2, i + 84, 98, 20, "Remove Enemy"));
    }



    @Override
    protected void actionPerformed(GuiButton p_146284_1_) throws IOException {
        if (selectedButton.id == 1) {
            EnemiesUtils.addEnemy(inputField.getText());
        }
        if (selectedButton.id == 2) {
            EnemiesUtils.removeEnemy(inputField.getText());
        }
    }

    @Override
    public void updateScreen() {
        inputField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException {
        if (p_73869_2_ == Keyboard.KEY_ESCAPE) {
            if (mc.player != null) {
                mc.displayGuiScreen(null);
            } else {
                mc.displayGuiScreen(new BThackSettingsGui());
            }
        } else {
            inputField.textboxKeyTyped(p_73869_1_, p_73869_2_);
        }
    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
        inputField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        GL11.glColor4f(1, 1, 1, 1);
        drawDefaultBackground();

        for (GuiButton guiButton : this.buttonList) {
            guiButton.drawButton(this.mc, p_73863_1_, p_73863_2_, p_73863_3_);
        }

        //for (int i = 0; i < this.buttonList.size(); i++) {
        //    (this.buttonList.get(i)).drawButton(this.mc, p_73863_1_, p_73863_2_, p_73863_3_);
        //}

        inputField.drawTextBox();
    }
}

package com.bt.BThack.impl.CustomGui.Gui;

import com.bt.BThack.impl.CustomGui.Menu.BThackMenu;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class BThackSettingsGui extends GuiScreen {
    public BThackSettingsGui() {
        super();
    }
    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        GL11.glColor4f(1, 1, 1, 1);
        drawDefaultBackground();

        //drawLogo.drawString(5, "BThack", (float) this.width / 10 - (float) this.fontRenderer.getStringWidth("BThack") / 2,
        //        (float) this.height / 20, 0x191CFF);

        for (GuiButton guiButton : this.buttonList) {
            guiButton.drawButton(this.mc, p_73863_1_, p_73863_2_, p_73863_3_);
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    public void initGui() {
        int i = this.height / 4 + 48;
        this.buttonList.clear();
        super.buttonList.add(new GuiButton(1, super.width / 2 - 100, i + 72 - 50, 200, 20, "Friends"));
        super.buttonList.add(new GuiButton(2, super.width / 2 - 100, i + 72 - 28, 200, 20, "Enemies"));
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException {
        if (p_73869_2_ == Keyboard.KEY_ESCAPE) {
            if (mc.player != null) {
                mc.displayGuiScreen(null);
            } else
                mc.displayGuiScreen(new BThackMenu());
        }
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) throws IOException {
        if (selectedButton.id == 1) {
            mc.displayGuiScreen(new FriendsGui());
        }
        if (selectedButton.id == 2) {
            mc.displayGuiScreen(new EnemiesGui());
        }
    }

}

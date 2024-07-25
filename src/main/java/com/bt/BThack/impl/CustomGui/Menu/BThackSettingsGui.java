package com.bt.BThack.impl.CustomGui.Menu;

import com.bt.BThack.api.Utils.System.BThackGui;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.impl.CustomGui.Menu.Tools.ChangeNicknameGui;
import com.bt.BThack.impl.CustomGui.Menu.Tools.EnemiesGui;
import com.bt.BThack.impl.CustomGui.Menu.Tools.FriendsGui;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class BThackSettingsGui extends BThackGui {
    public BThackSettingsGui() {
        super();
    }
    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        GL11.glColor4f(1, 1, 1, 1);
        if (mc.player != null) {
            drawDefaultBackground();
        } else {
            mc.renderEngine.bindTexture(ChangeNicknameGui.MainTexture);
            Gui.drawScaledCustomSizeModalRect(0,0,0,0, this.width, this.height, this.width, this.height, this.width, this.height);
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    public void initGui() {
        int i = this.height / 4 + 48;
        this.buttons.clear();
        super.buttons.add(new Button(1, this.width / 2, i + 72 - 60, 100, 10, "Friends"));
        super.buttons.add(new Button(2, this.width / 2, i + 72 - 38, 100, 10, "Enemies"));
    }

    @Override
    protected void keyTyped(char charKey, int p_73869_2_) {
        if (p_73869_2_ == Keyboard.KEY_ESCAPE) {
            if (mc.player != null) {
                mc.displayGuiScreen(null);
            } else
                mc.displayGuiScreen(new BThackMenu());
        }
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (activeButton.getId() == 1) {
            mc.displayGuiScreen(new FriendsGui());
        }
        if (activeButton.getId() == 2) {
            mc.displayGuiScreen(new EnemiesGui());
        }
    }
}

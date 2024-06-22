package com.bt.BThack.impl.CustomGui.Menu;

import com.bt.BThack.impl.CustomGui.Gui.BThackSettingsGui;
import com.bt.BThack.impl.CustomGui.Menu.Tools.ChangeNicknameGui;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

public class BThackMenu extends GuiScreen {
    ResourceLocation MainTexture = new ResourceLocation("bthack", "menu1.png");
    ResourceLocation BThackLogo = new ResourceLocation("bthack", "bthacklogo.png");

    public BThackMenu() {
        super();
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        glColor4f(1, 1, 1, 1);
        drawDefaultBackground();
        mc.renderEngine.bindTexture(MainTexture);
        Gui.drawScaledCustomSizeModalRect(0,0,0,0, this.width, this.height, this.width, this.height, this.width, this.height);

        mc.renderEngine.bindTexture(BThackLogo);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        Gui.drawScaledCustomSizeModalRect(this.width / 10, this.height / 10, 0, 0, this.width / 2, this.height / 2, this.width / 2, this.height / 2, (float) this.width / 2, (float) this.height / 2);
        glDisable(GL_BLEND);
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
        this.buttonList.add(new GuiButton(0, this.width / 2 + 100, i + 72 + 56, 98,
                20, "Options"));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 202, i + 72 + 56, 98,
                20, "Quit"));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 100, i + 72 + 12, 200,
                20, "Change Nickname"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 100, i + 72 + 34, 200,
                20, "BThack Settings"));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 100, i + 72 - 12, 200,
                20, "Multiplayer"));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 100, i + 72 - 34, 200,
                20, "Singleplayer"));
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) throws IOException {
        if (selectedButton.id == 0) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        } if (selectedButton.id == 1) {
            mc.shutdown();
        } if (selectedButton.id == 2) {
            mc.displayGuiScreen(new ChangeNicknameGui());
        } if (selectedButton.id == 3) {
            mc.displayGuiScreen(new BThackSettingsGui());
        } if (selectedButton.id == 4) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        } if (selectedButton.id == 5) {
            mc.displayGuiScreen(new GuiWorldSelection(this));
        }
        super.actionPerformed(p_146284_1_);
    }
}

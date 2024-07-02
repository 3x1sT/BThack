package com.bt.BThack.impl.CustomGui.Menu;

import com.bt.BThack.BThack;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.MusicManager.MainMenuMusicThread;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.Utils.System.BThackGui;
import com.bt.BThack.api.Utils.System.Button;
import com.bt.BThack.impl.CustomGui.Menu.Tools.ChangeNicknameGui;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import static org.lwjgl.opengl.GL11.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BThackMenu extends BThackGui {

    ResourceLocation MainTexture = new ResourceLocation("bthack", "menu1.png");
    public static ResourceLocation BThackLogo = new ResourceLocation("bthack", "bthacklogo.png");

    public BThackMenu() {
        super();
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        glColor4f(1, 1, 1, 1);
        drawDefaultBackground();
        mc.renderEngine.bindTexture(MainTexture);
        RenderUtils.drawScaledCustomSizeModalRect(0,0,0,0, this.width, this.height, this.width, this.height, this.width, this.height);

        mc.renderEngine.bindTexture(BThackLogo);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        RenderUtils.drawScaledCustomSizeModalRect(this.width / 10, this.height / 10, 0, 0, 324, 169, 324, 169, 324, 169);
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
        //int i = this.height / 4 + 48;
        this.buttons.clear();

        this.buttons.add(new Button(5, this.width - 127, this.height - 144, 125,
                12, "Singleplayer"));
        this.buttons.add(new Button(4, this.width - 127, this.height - 118, 125,
                12, "Multiplayer"));
        this.buttons.add(new Button(2, this.width - 127, this.height - 92, 125,
                12, "Change Nickname"));
        this.buttons.add(new Button(3, this.width - 127, this.height - 66, 125,
                12, "BThack Settings"));
        this.buttons.add(new Button(0, this.width - 127, this.height - 40, 125,
                12, "Options"));
        this.buttons.add(new Button(1, this.width - 127, this.height - 14, 125,
                12, "Quit"));

        this.buttons.add(new Button(6, 66, this.height - 16, 62,
                12, "My Discord channel"));


        if (Client.mainMenuMusicThread != null) {
            if (!MainMenuMusicThread.started)
                Client.mainMenuMusicThread.start();
        }
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX,mouseY,mouseButton);

        if (activeButton.getId() == 0) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        } if (activeButton.getId() == 1) {
            mc.shutdown();
        } if (activeButton.getId() == 2) {
            mc.displayGuiScreen(new ChangeNicknameGui());
        } if (activeButton.getId() == 3) {
            mc.displayGuiScreen(new BThackSettingsGui());
        } if (activeButton.getId() == 4) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        } if (activeButton.getId() == 5) {
            mc.displayGuiScreen(new GuiWorldSelection(this));
        } if (activeButton.getId() == 6) {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI("https://discord.gg/xecWXN97s6"));
                } catch (URISyntaxException e) {
                    BThack.error("An error occurred while trying to open the discord link!");
                }
            }
        }
    }
}

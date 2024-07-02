package com.bt.BThack.impl.CustomGui.Menu.Tools;

import com.bt.BThack.BThack;
import com.bt.BThack.api.Utils.System.BThackGui;
import com.bt.BThack.api.Utils.System.Button;
import com.bt.BThack.impl.CustomGui.Menu.BThackMenu;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.net.Proxy;

public class ChangeNicknameGui extends BThackGui {
    public static ResourceLocation MainTexture = new ResourceLocation("bthack", "menulow.png");
    GuiTextField inputField;

    @Override
    public void initGui() {
        int i = this.height / 4 + 48;

        this.buttons.clear();
        inputField = new GuiTextField(1, fontRenderer, this.width / 2 - 100, i + 60, 200, 20);

        inputField.setText("Nickname");

        this.buttons.add(new Button(1, width / 2, i + 94, 100, 10, "Confirm"));
    }

    public static void changeNickname(String Nickname) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.logOut();

        Session session = new Session(Nickname, Nickname, "0", "legacy");

        try {
            BThack.setSession(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScreen() {
        inputField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
        switch (p_73869_2_) {
            case Keyboard.KEY_ESCAPE:
                mc.displayGuiScreen(new BThackMenu());
                break;
            default:
                inputField.textboxKeyTyped(p_73869_1_, p_73869_2_);
        }
    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
        inputField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);

        if (activeButton.getId() == 1) {
            changeNickname(inputField.getText());
            mc.displayGuiScreen(new BThackMenu());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glColor4f(1, 1, 1, 1);
        drawDefaultBackground();
        mc.renderEngine.bindTexture(MainTexture);
        Gui.drawScaledCustomSizeModalRect(0,0,0,0, this.width, this.height, this.width, this.height, this.width, this.height);

        inputField.drawTextBox();
        mc.fontRenderer.drawStringWithShadow("Your Nickname: " + mc.getSession().getUsername(), this.width / 2 - 100, this.height / 4 + 48 + 110, Color.yellow.getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

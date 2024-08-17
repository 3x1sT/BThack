package com.bt.BThack.impl.CustomGui.Gui.Client.HudMover;

import com.bt.BThack.System.Client;
import com.bt.BThack.System.ConfigInit.ConfigInit;
import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.HudMover.Utils.HudComponentButton;
import com.google.common.collect.Sets;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.Set;

public class HudMoverGui extends GuiScreen {
    private final Set<HudComponentButton> hudComponentButtons = Sets.newHashSet();

    @Override
    public void initGui() {
        for (HudComponent component : Client.hudComponents) {
            hudComponentButtons.add(new HudComponentButton(0, component));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sc = new ScaledResolution(mc);
        RenderUtils.draw4ColorRect( 0, 0, sc.getScaledWidth(), sc.getScaledHeight(), new Color(0,0,0, 128).hashCode(), new Color(0,0,0, 128).hashCode(), new Color(161,0, 255, 255).hashCode(), new Color(255, 0, 0, 255).hashCode());

        for (HudComponentButton button : hudComponentButtons) {
            button.updateButton(mouseX, mouseY);
            button.renderButton();
        }
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (HudComponentButton button : hudComponentButtons) {
            button.mouseClicked(mouseX, mouseY);
        }
    }

    @Override
    public void onGuiClosed() {
        try {
            ConfigInit.saveHudComponents();
        } catch (IOException ignored) {}
    }
}

package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

public class TPSComponent extends HudComponent {

    public TPSComponent(Module module) {
        super("TPS",
                5,
                125,
                true,
                module
        );
    }

    @Override
    public void render(RenderType type) {
        if (nullCheck()) return;

        String renderString = String.format("TPS%s %.2f", ChatFormatting.WHITE, Client.tpsManager.getTickRate());
        drawText(renderString, getX(), getY());
        this.width = FontUtil.getStringWidth(renderString);
        this.height = FontUtil.getStringHeight(renderString);
    }
}

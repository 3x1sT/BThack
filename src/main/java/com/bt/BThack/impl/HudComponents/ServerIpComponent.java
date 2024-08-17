package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.Objects;

public class ServerIpComponent extends HudComponent {

    public ServerIpComponent(Module module) {
        super("ServerIp",
                5,
                95,
                true,
                module
        );

        addCheckbox("Short", false);
    }

    @Override
    public void render(RenderType type) {
        String text = "";

        if (mc.integratedServerIsRunning) {
            text = !getCheckbox("Short") ? ("IP " + ChatFormatting.WHITE + "Singleplayer") : (ChatFormatting.WHITE + "Singleplayer");
        } else {
            text = !getCheckbox("Short") ? ("IP " + ChatFormatting.WHITE + Objects.requireNonNull(mc.getCurrentServerData()).serverIP) : (ChatFormatting.WHITE + Objects.requireNonNull(mc.getCurrentServerData()).serverIP);
        }

        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
        drawText(text, getX(), getY());
    }
}

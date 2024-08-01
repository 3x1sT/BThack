package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PingComponent extends HudComponent {
    public PingComponent(Module module) {
        super("Ping",
                5,
                115,
                true,
                module
        );

        addMode("Ping Mode", new ArrayList<>(Arrays.asList("Normal", "Short")));
    }

    @Override
    public void render(RenderType type) {
        if (nullCheck()) return;

        String renderString;

        switch (getMode("Ping Mode")) {
            case "Normal":
                renderString = "Ping " + ChatFormatting.WHITE + getPing() + "ms";
                this.width = FontUtil.getStringWidth(renderString);
                this.height = FontUtil.getStringHeight(renderString);
                drawText(renderString, getX(), getY());
                break;
            case "Short":
                renderString = "" + ChatFormatting.WHITE + getPing() + "ms";
                this.width = FontUtil.getStringWidth(renderString);
                this.height = FontUtil.getStringHeight(renderString);
                drawText(renderString, getX(), getY());
                break;
        }
    }

    private int getPing() {
        if (mc.player != null && mc.getConnection() != null && mc.getConnection().getPlayerInfo(mc.player.getName()) != null) {
            return Objects.requireNonNull(mc.getConnection().getPlayerInfo(mc.player.getName())).getResponseTime();
        }

        return -1;
    }
}

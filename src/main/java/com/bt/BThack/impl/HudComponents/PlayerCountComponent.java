package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

public class PlayerCountComponent extends HudComponent {

    public PlayerCountComponent(Module module) {
        super("PlayerCount",
                5,
                135,
                true,
                module
        );
    }

    @Override
    public void render(RenderType type) {
        if (nullCheck()) return;

        String renderString = "Players " + ChatFormatting.WHITE + mc.player.connection.getPlayerInfoMap().size();
        drawText(renderString, getX(), getY());
        this.width = FontUtil.getStringWidth(renderString);
        this.height = FontUtil.getStringHeight(renderString);
    }
}

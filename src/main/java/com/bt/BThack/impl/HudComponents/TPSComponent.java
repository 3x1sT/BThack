package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Managers.TPSManager;
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

        String renderString = String.format("TPS%s %.2f", ChatFormatting.WHITE, TPSManager.Get().getTickRate());
        drawText(renderString, getX(), getY());
        this.width = FontUtil.getStringWidth(renderString);
        this.height = FontUtil.getStringHeight(renderString);
    }
}

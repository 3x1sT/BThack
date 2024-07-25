package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

public class DirectionComponent extends HudComponent {

    public DirectionComponent(Module module) {
        super("Direction",
                5,
                85,
                true,
                module
        );
    }

    @Override
    public void render(RenderType t) {
        if (nullCheck()) return;

        outOfBoundsCheck();

        String direction = AimBotUtils.getDirection(mc.player);

        String text = "Direction: " + ChatFormatting.WHITE + direction;

        drawText(text,
                this.getX(), this.getY());

        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

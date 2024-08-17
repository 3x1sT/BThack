package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

public class DimensionComponent extends HudComponent {

    public DimensionComponent(Module module) {
        super("Dimension",
                5,
                205,
                false,
                module
        );
    }

    @Override
    public void render(RenderType type) {
        String text = "";
        switch (mc.player.dimension) {
            case -1:
                text = "Dimension: " + ChatFormatting.WHITE + "Nether";
                break;
            case 0:
                text = "Dimension: " + ChatFormatting.WHITE + "Overworld";
                break;
            default:
                text = "Dimension: " + ChatFormatting.WHITE + "End";
                break;
        }

        drawText(text, getX(), getY());
        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

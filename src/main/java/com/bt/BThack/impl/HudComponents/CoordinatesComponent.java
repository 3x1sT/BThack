package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.text.DecimalFormat;

public class CoordinatesComponent extends HudComponent {

    public CoordinatesComponent(Module module) {
        super("Coordinates",
                5,
                57,
                true,
                module
        );
    }

    @Override
    public void render(RenderType t) {
        if (nullCheck()) return;

        outOfBoundsCheck();

        String overWorldX;
        String overWorldZ;
        String netherX;
        String netherZ;
        if (mc.player.world.provider.isNether()) {
            overWorldX = new DecimalFormat(pattern).format(mc.player.posX * 8);
            overWorldZ = new DecimalFormat(pattern).format(mc.player.posZ * 8.0D);
            netherX = new DecimalFormat(pattern).format(mc.player.posX);
            netherZ = new DecimalFormat(pattern).format(mc.player.posZ);
        } else {
            overWorldX = new DecimalFormat(pattern).format(mc.player.posX);
            overWorldZ = new DecimalFormat(pattern).format(mc.player.posZ);
            netherX = new DecimalFormat(pattern).format(mc.player.posX / 8.0D);
            netherZ = new DecimalFormat(pattern).format(mc.player.posZ / 8.0D);
        }

        String xyzText = "XYZ: " + ChatFormatting.WHITE + overWorldX + " " + Math.round(mc.player.posY) + " " + overWorldZ;
        String netherText = "Nether: " + ChatFormatting.WHITE + netherX + " " + Math.round(mc.player.posY) + " " + netherZ;

        drawText(xyzText, this.getX(), this.getY());
        drawText(netherText, this.getX(), this.getY() + 9.3f);

        this.width = Math.max(FontUtil.getStringWidth(xyzText), FontUtil.getStringWidth(netherText));
        this.height = 9.3f + FontUtil.getStringHeight(netherText);
    }
}

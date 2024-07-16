package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.text.DecimalFormat;

public class RotationComponent extends HudComponent {

    public RotationComponent(Module module) {
        super("Rotation",
                5,
                75,
                true,
                module
        );
    }

    @Override
    public void render(RenderType t) {
        if (nullCheck()) return;

        outOfBoundsCheck();

        String yaw;
        String pitch;
        double preYaw;

        preYaw = mc.player.rotationYaw / 360;
        if (preYaw < 0) {
            preYaw = -preYaw;
            preYaw = (-(preYaw - ((int) preYaw))) * 360;
        } else {
            preYaw = (preYaw - ((int) preYaw)) * 360;
        }

        yaw = new DecimalFormat(pattern).format(preYaw);

        pitch = new DecimalFormat(pattern).format(mc.player.rotationPitch);

        String text = "Yaw: " + ChatFormatting.WHITE + yaw + ChatFormatting.RESET + " " +
                "Pitch: " + ChatFormatting.WHITE + pitch;

        drawText(text,
                this.getX(), this.getY());

        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

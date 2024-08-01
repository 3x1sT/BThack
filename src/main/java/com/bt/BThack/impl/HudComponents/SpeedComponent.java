package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.SpeedMathThread;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.text.DecimalFormat;

public class SpeedComponent extends HudComponent {

    public SpeedComponent(Module module) {
        super("Speed",
                5,
                105,
                true,
                module
        );
    }

    @Override
    public void render(RenderType t) {
        if (nullCheck()) return;

        outOfBoundsCheck();

        String speed = new DecimalFormat(pattern).format(SpeedMathThread.speed);

        String text = "Speed: " + ChatFormatting.WHITE + speed + "b/s";

        drawText(text,
                this.getX(), this.getY());

        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

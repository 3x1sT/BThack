package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;

public class FPSComponent extends HudComponent {

    public FPSComponent(Module module) {
        super("FPS",
                5,
                47,
                true,
                module
        );
    }
    private int maxFPS = 0;

    @Override
    public void render(RenderType t) {
        if (nullCheck()) return;

        outOfBoundsCheck();

        int fps = Minecraft.getDebugFPS();

        if (fps > maxFPS)
            maxFPS = fps;

        String text = "FPS: " + ChatFormatting.WHITE + Minecraft.getDebugFPS() + ChatFormatting.RESET + " (Max: " + ChatFormatting.WHITE + maxFPS + ChatFormatting.RESET + ")";

        drawText(text, this.getX(), this.getY());
        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

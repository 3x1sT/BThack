package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class RealTimeComponent extends HudComponent {

    public RealTimeComponent(Module module) {
        super("RealTime",
                new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 2f,
                10,
                true,
                module
        );

        addMode("Hour Mode", new ArrayList<>(Arrays.asList("24", "12")));
    }

    @Override
    public void render(RenderType type) {
        String text = "Time " + ChatFormatting.WHITE + (getMode("Hour Mode").equals("12") ? new SimpleDateFormat("h:mm").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date()));
        drawText(text, getX(), getY());
        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

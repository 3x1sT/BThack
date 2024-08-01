package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;

public class BiomeComponent extends HudComponent {
    public BiomeComponent(Module module) {
        super("Biome",
                5,
                145,
                true,
                module
        );
        addMode("Mode", new ArrayList<>(Arrays.asList("Normal", "Short")));
    }

    @Override
    public void render(RenderType type) {
        String text = "";

        switch (getMode("Mode")) {
            case "Normal":
                text = "Biome " + ChatFormatting.WHITE + mc.world.getBiome(mc.player.getPosition()).getBiomeName();



                this.drawText(text, getX(), getY());
                break;
            case "Short":
                text = ChatFormatting.WHITE + mc.world.getBiome(mc.player.getPosition()).getBiomeName();
                this.drawText(text, getX(), getY());
                break;
        }

        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

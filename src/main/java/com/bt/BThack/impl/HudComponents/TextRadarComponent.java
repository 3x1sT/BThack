package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;

public class TextRadarComponent extends HudComponent {

    public TextRadarComponent(Module module) {
        super("Text Radar",
                250,
                5,
                true,
                module
        );
    }

    @Override
    public void render(RenderType type) {
        if (type == RenderType.TEXT) {
            float y = 0;
            float maxWidth = 0;
            for (EntityPlayer player : mc.world.playerEntities) {
                if (player.getDisplayNameString().equals(mc.player.getDisplayNameString())) continue;
                String text = player.getDisplayNameString() + " " + ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + decimal.format(player.getDistance(mc.player)) + "m." + ChatFormatting.GRAY + "]";

                drawText(text, getX(), getY() + y);


                if (maxWidth < FontUtil.getStringWidth(text)) {
                    maxWidth = FontUtil.getStringWidth(text);
                }
                y += FontUtil.getStringHeight(text) + 1;
            }

            this.width = maxWidth;
            this.height = y;
        }
    }
}

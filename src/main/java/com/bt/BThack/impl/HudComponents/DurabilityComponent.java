package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.ItemsUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.EnumHand;

public class DurabilityComponent extends HudComponent {

    public DurabilityComponent(Module module) {
        super("Durability",
                5,
                215,
                false,
                module
        );
    }

    @Override
    public void render(RenderType type) {
        String text = "Durability: " + ChatFormatting.WHITE + ItemsUtil.getItemDurability(mc.player.getHeldItem(EnumHand.MAIN_HAND));

        drawText(text, getX(), getY());
        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

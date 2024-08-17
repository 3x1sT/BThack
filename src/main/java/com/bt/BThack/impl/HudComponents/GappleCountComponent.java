package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GappleCountComponent extends HudComponent {

    public GappleCountComponent(Module module) {
        super("GappleCount",
                5,
                245,
                false,
                module
        );
    }

    @Override
    public void render(RenderType type) {
        int gapples = mc.player.inventory.mainInventory.stream()
                .filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE)
                .mapToInt(ItemStack::getCount)
                .sum();

        if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            gapples += mc.player.getHeldItemOffhand().getCount();
        }

        String text = "Gapples: " + ChatFormatting.WHITE + gapples;

        drawText(text, getX(), getY());
        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

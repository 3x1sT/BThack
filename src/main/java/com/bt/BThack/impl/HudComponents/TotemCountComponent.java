package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TotemCountComponent extends HudComponent {

    public TotemCountComponent(Module module) {
        super("TotemCount",
                5,
                255,
                false,
                module
        );
    }

    @Override
    public void render(RenderType type) {
        int totems = mc.player.inventory.mainInventory.stream()
                .filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING)
                .mapToInt(ItemStack::getCount)
                .sum();

        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            totems += mc.player.getHeldItemOffhand().getCount();
        }

        String text = "Totems: " + ChatFormatting.WHITE + totems;

        drawText(text, getX(), getY());
        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

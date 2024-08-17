package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class EXPCountComponent extends HudComponent {

    public EXPCountComponent(Module module) {
        super("EXPCount",
                5,
                235,
                false,
                module
        );
    }

    @Override
    public void render(RenderType type) {
        int crystals = mc.player.inventory.mainInventory.stream()
                .filter(itemStack -> itemStack.getItem() == Items.EXPERIENCE_BOTTLE)
                .mapToInt(ItemStack::getCount)
                .sum();

        if (mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            crystals += mc.player.getHeldItemOffhand().getCount();
        }

        String text = "EXP Bottles: " + ChatFormatting.WHITE + crystals;

        drawText(text, getX(), getY());
        this.width = FontUtil.getStringWidth(text);
        this.height = FontUtil.getStringHeight(text);
    }
}

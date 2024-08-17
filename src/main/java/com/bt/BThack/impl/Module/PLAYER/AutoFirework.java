package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.InventoryUtils;
import com.bt.BThack.api.Utils.ItemsUtils;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

public class AutoFirework extends Module {
    int oldSlot = -1;
    public AutoFirework() {
        super("AutoFirework",
                "When enabled uses fireworks if the player is flying.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (mc.player != null && mc.player.isElytraFlying()) {
            oldSlot = mc.player.inventory.currentItem;
            int inventorySlot = InventoryUtils.findItem(Items.FIREWORKS);
            if (inventorySlot != -1) {
                InventoryUtils.swapItem(inventorySlot);
                ItemsUtils.useItem(EnumHand.MAIN_HAND);
                InventoryUtils.swapItem(oldSlot);
            }
        }
        toggle();
    }
}

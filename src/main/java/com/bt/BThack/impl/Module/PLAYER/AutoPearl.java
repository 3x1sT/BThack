package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.InventoryUtils;
import com.bt.BThack.api.Utils.ItemsUtils;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

public class AutoPearl extends Module {
    int oldSlot = -1;

    public AutoPearl() {

        super("AutoPearl",
                "When turned on, it throws pearl.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (mc.player != null) {
            oldSlot = mc.player.inventory.currentItem;
            int inventorySlot = InventoryUtils.findItem(Items.ENDER_PEARL);
            if (inventorySlot != -1) {
                InventoryUtils.swapItem(inventorySlot);
                ItemsUtils.useItem(EnumHand.MAIN_HAND);
                InventoryUtils.swapItem(oldSlot);
            }
            toggle();
        }
    }
}

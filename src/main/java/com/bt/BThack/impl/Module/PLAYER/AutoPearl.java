package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ItemsUtil;
import com.bt.BThack.api.Utils.Modules.AutoPearlUtil;
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
            int inventorySlot = AutoPearlUtil.getPearls();
            if (inventorySlot != -1) {
                mc.player.inventory.currentItem = inventorySlot;
                ItemsUtil.useItem(EnumHand.MAIN_HAND);
                mc.player.inventory.currentItem = oldSlot;
            }
            toggle();
        }
    }
}

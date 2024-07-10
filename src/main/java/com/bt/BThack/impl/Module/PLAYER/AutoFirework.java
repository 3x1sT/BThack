package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ItemsUtil;
import com.bt.BThack.api.Utils.Modules.AutoFireworkUtil;
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
            int inventorySlot = AutoFireworkUtil.getFireworks();
            if (inventorySlot != -1) {
                ItemsUtil.swapItem(inventorySlot);
                ItemsUtil.useItem(EnumHand.MAIN_HAND);
                ItemsUtil.swapItem(oldSlot);
            }
        }
        toggle();
    }
}

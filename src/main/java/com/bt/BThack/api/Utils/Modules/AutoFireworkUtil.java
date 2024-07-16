package com.bt.BThack.api.Utils.Modules;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemStack;

public class AutoFireworkUtil implements Mc {
    public static int getFireworks() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemFirework) {
                return i;
            }
        }
        return -1;
    }
}

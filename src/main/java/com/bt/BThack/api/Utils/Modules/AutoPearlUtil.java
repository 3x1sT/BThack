package com.bt.BThack.api.Utils.Modules;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;

public class AutoPearlUtil implements Mc {
    public static int getPearls() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemEnderPearl) {
                return i;
            }
        }
        return -1;
    }
}

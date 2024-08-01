package com.bt.BThack.api.Utils.Modules;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.item.ItemSword;

public class AutoSwordUtil implements Mc {
    public static int getSword() {
        for(int i = 0; i < 9; ++i) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemSword) {
                return i;
            }
        }

        return -1;
    }
}

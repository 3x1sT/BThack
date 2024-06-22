package com.bt.BThack.api.Utils;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class ItemsUtil implements Mc {
    public static void useItem(EnumHand hand) {
        mc.player.swingArm(hand);
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(hand));
    }

    public static int getBlock() {
        for(int i = 0; i < 9; ++i) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                return i;
            }
        }
        return -1;
    }
}

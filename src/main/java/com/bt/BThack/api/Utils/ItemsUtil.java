package com.bt.BThack.api.Utils;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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

    public static void swapItem(int needSlot) {
        mc.player.inventory.currentItem = needSlot;
        mc.playerController.updateController();
    }

    public static ItemStack getItem(int hotbarSlot) {
        return mc.player.inventory.getStackInSlot(hotbarSlot);
    }
}

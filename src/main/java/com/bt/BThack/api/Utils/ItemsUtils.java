package com.bt.BThack.api.Utils;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class ItemsUtils implements Mc {
    public static void useItem(EnumHand hand) {
        mc.player.swingArm(hand);
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(hand));
    }

    public static void useItemNoSwing(EnumHand hand) {
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

    public static int getItemDurability(ItemStack itemStack) {
        return itemStack.getMaxDamage() - itemStack.getItemDamage();
    }

    public static int getItemMaxDurability(ItemStack itemStack) {
        return itemStack.getMaxDamage();
    }

    public static float getItemDurabilityInPercentages(ItemStack itemStack) {
        return 100f - (((float)itemStack.getItemDamage() / (float)itemStack.getMaxDamage()) * 100f);
    }
}

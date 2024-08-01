package com.bt.BThack.api.Utils;

import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class ItemsUtil implements Mc {
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

    public static void swapItem(int needSlot) {
        mc.player.inventory.currentItem = needSlot;
        mc.playerController.updateController();
    }

    public static ItemStack getItem(int hotbarSlot) {
        return mc.player.inventory.getStackInSlot(hotbarSlot);
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

    public static void replaceItems(int slot1, int slot2) {
        ThreadManager.startNewThread(thread -> {
            mc.playerController.windowClick(0, slot1, 1, ClickType.PICKUP, mc.player);
            try {
                thread.sleep(50);
            } catch (InterruptedException e) {}
            mc.playerController.windowClick(0, slot2, 1, ClickType.PICKUP, mc.player);

            try {
                thread.sleep(50);
            } catch (InterruptedException e) {}
            mc.playerController.windowClick(0, slot1, 1, ClickType.PICKUP, mc.player);

            mc.playerController.updateController();
        });
    }
}

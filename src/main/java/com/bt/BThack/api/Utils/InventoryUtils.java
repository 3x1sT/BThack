package com.bt.BThack.api.Utils;

import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtils implements Mc {

    public static final int HEAD_SLOT = 5;
    public static final int CHESTPLATE_SLOT = 6;
    public static final int LEGS_SLOT = 7;
    public static final int FEET_SLOT = 8;
    public static final int OFFHAND_SLOT = 45;

    public static void swapItem(int needSlot) {
        mc.player.inventory.currentItem = needSlot;
        mc.playerController.updateController();
    }

    public static void swapItemOnInventory(int needHotbarSlot ,int inventorySlot) {
        mc.playerController.updateController();
        mc.playerController.windowClick(0, inventorySlot, needHotbarSlot, ClickType.SWAP, mc.player);
        mc.playerController.updateController();
    }

    public static int findItem(ItemStack stack) {
        for (int i = 0; i < 36; i++) {
            if (mc.player.inventory.getStackInSlot(i) == stack) {
                return i;
            }
        }
        return -1;
    }

    public static int findItem(Item item) {
        for (int i = 0; i < 36; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    public static ItemStack getItem(int hotbarSlot) {
        return mc.player.inventory.getStackInSlot(hotbarSlot);
    }

    public static void replaceItems(int slot1, int slot2, int delay) {
        ThreadManager.startNewThread(thread -> {
            mc.playerController.windowClick(0, slot1, 1, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
            if (delay > 0) {
                try {
                    thread.sleep(delay);
                } catch (InterruptedException e) {
                }
            }
            mc.playerController.windowClick(0, slot2, 1, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
            if (delay > 0) {
                try {
                    thread.sleep(delay);
                } catch (InterruptedException e) {
                }
            }
            mc.playerController.windowClick(0, slot1, 1, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
        });
    }
}

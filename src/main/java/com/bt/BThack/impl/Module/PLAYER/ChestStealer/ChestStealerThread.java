package com.bt.BThack.impl.Module.PLAYER.ChestStealer;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;

public class ChestStealerThread extends Thread{
    public void run() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.player.openContainer;
            for (int index = 0; index < container.inventorySlots.size(); ++index) {
                if (mc.player.openContainer instanceof ContainerChest && ChestStealer.active == 1) {
                    if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0)) {
                        mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                        new StealerTimer().start();
                        if (StealerTimer.allow != 1) {
                            return;
                        }
                        StealerTimer.allow = 0;
                        continue;
                    }

                    if (container.getLowerChestInventory().isEmpty()) {
                        mc.player.closeScreen();
                    }
                }
            }
        }
    }
}

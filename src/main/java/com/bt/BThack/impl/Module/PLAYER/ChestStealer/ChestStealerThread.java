package com.bt.BThack.impl.Module.PLAYER.ChestStealer;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;

public class ChestStealerThread extends Thread{
    public void run() {
        long time = (long) Module.getSlider("ChestStealer", "Steal Delay");
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.player.openContainer;
            if (container.getLowerChestInventory().isEmpty()) {
                while (mc.currentScreen instanceof GuiContainer) {
                    try {
                        sleep(100);
                    } catch (InterruptedException ignored) {}
                }
                ChestStealer.active = false;
                stop();
            }
            for (int index = 0; index < container.inventorySlots.size(); ++index) {
                if (mc.player.openContainer instanceof ContainerChest && Client.getModuleByName("ChestStealer").isEnabled()) {
                    if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0)) {
                        mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                        try {
                            sleep(time);
                        } catch (InterruptedException ignored) {}
                    }

                    if (container.getLowerChestInventory().isEmpty()) {
                        mc.player.closeScreen();
                        ChestStealer.active = false;
                        break;
                    }
                }
            }
        }
    }
}

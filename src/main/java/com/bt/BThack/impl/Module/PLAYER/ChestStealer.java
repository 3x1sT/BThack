package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ChestStealer extends Module {
    public static boolean active = false;
    public ChestStealer() {
        super("ChestStealer",
                "Steals items from a chest.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addSlider("Steal Delay", this, 100,0,1000,true);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player.openContainer instanceof ContainerChest) {
            if (!active) {
                ThreadManager.startNewThread(thread -> {
                    long time = (long) Module.getSlider("ChestStealer", "Steal Delay");
                    if (mc.player.openContainer instanceof ContainerChest) {
                        ContainerChest container = (ContainerChest) mc.player.openContainer;
                        if (container.getLowerChestInventory().isEmpty()) {
                            while (mc.currentScreen instanceof GuiContainer) {
                                try {
                                    thread.sleep(100);
                                } catch (InterruptedException ignored) {}
                            }
                            ChestStealer.active = false;
                            thread.stop();
                        }
                        for (int index = 0; index < container.inventorySlots.size(); ++index) {
                            if (mc.player.openContainer instanceof ContainerChest && Client.getModuleByName("ChestStealer").isEnabled()) {
                                if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0)) {
                                    mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                                    try {
                                        thread.sleep(time);
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
                });
                active = true;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        active = false;
    }
}

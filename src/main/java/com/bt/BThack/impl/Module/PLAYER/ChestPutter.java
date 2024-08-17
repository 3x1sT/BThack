package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BTbot.api.Utils.Generate.GenerateNumber;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.MathUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;
import java.util.Set;

public class ChestPutter extends Module {

    public static Set<Item> putItems = new HashSet<>();

    public ChestPutter() {
        super("ChestPutter",
                "Automatically stores the items you need in a chest.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addSlider("Put Delay", this, 100,0,1000,true);

        addCheckbox("Auto Close", this, true);

        addCheckbox("Delay Spread", this, true);
        addSlider("Delay Spread", true, "Min Spread", this, 0.8, 0.5, 1, false);
        addSlider("Delay Spread", true, "Max Spread", this, 1.2, 1, 1.5, false);

        addCheckbox("More Clicks", this, false);
        addSlider("More Clicks", true, "Clicks", this, 3, 2, 6, true);
        addSlider("Click delay", this, 5, 0, 20, true);
    }

    public static volatile boolean active = false;

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player.openContainer instanceof ContainerChest) {
            if (!active) {
                active = true;
                ThreadManager.startNewThread(thread -> {

                    long time = (long) Module.getSlider("ChestPutter", "Put Delay");
                    if (mc.player.openContainer instanceof ContainerChest) {

                        ContainerChest container = (ContainerChest) mc.player.openContainer;
                        if (mc.player.inventory.mainInventory.isEmpty()) {
                            ChatUtils.sendMessage("Inventory empty");
                            while (mc.currentScreen instanceof GuiContainer) {
                                try {
                                    thread.sleep(100);
                                } catch (InterruptedException ignored) {}
                            }
                            ChestPutter.active = false;
                            thread.stop();
                        }

                        for (int index = 0; index < 36; index++) {
                            if (mc.player.openContainer instanceof ContainerChest && Client.getModuleByName("ChestPutter").isEnabled()) {
                                Item item = mc.player.inventory.getStackInSlot(index).getItem();
                                if (ChestPutter.putItems.contains(item)) {

                                    //It's just freaking math, it's just sexy math, it's just genius math, it's just the best math.
                                    //Do you like math? You have to love math! Math is everything!
                                    //
                                    //I'm going to go crazy soon.
                                    if (index > 8 && index <= 17) {
                                        index += 18;
                                    } else if (index > 26)
                                        index -= 18;

                                    int swapSlot = (35 - ((index - MathUtils.removeNumbers(index + 1, 9)) + MathUtils.mirrorNumber(1, MathUtils.removeNumbers(index + 1, 9 ), 8))) - 1 + container.getLowerChestInventory().getSizeInventory();

                                    if (getCheckbox(this.name, "More Clicks")) {
                                        for (int i = 0; i < (int) getSlider(this.name, "Clicks"); i++) {
                                            mc.playerController.windowClick(container.windowId, swapSlot, 0, ClickType.QUICK_MOVE, mc.player);
                                            mc.playerController.updateController();

                                            try {
                                                thread.sleep((int) getSlider(this.name, "Click delay"));
                                            } catch (InterruptedException ignored) {}
                                        }
                                    } else {
                                        mc.playerController.windowClick(container.windowId, swapSlot, 0, ClickType.QUICK_MOVE, mc.player);
                                        mc.playerController.updateController();
                                    }

                                    try {
                                        if (getCheckbox("ChestPutter", "Delay Spread")) {
                                            if (GenerateNumber.generateInt(0, 1) == 0) {
                                                thread.sleep((long) (time * GenerateNumber.generateFloat((float) getSlider(this.name, "Min Spread"), 1)));
                                            } else {
                                                thread.sleep((long) (time * GenerateNumber.generateFloat(1, (float) getSlider(this.name, "Max Spread"))));
                                            }
                                        } else {
                                            thread.sleep(time);
                                        }
                                    } catch (InterruptedException ignored) {}
                                }

                                if (mc.player.inventory.mainInventory.isEmpty()) {
                                    mc.player.closeScreen();
                                    ChestPutter.active = false;
                                    break;
                                }
                            }
                        }
                        if (getCheckbox("ChestPutter", "Auto Close")) {
                            mc.player.closeScreen();
                            ChestPutter.active = false;
                        } else {
                            while (mc.currentScreen instanceof GuiContainer) {
                                try {
                                    thread.sleep(100);
                                } catch (InterruptedException ignored) {}
                            }
                            ChestPutter.active = false;
                            thread.stop();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        active = false;
    }
}

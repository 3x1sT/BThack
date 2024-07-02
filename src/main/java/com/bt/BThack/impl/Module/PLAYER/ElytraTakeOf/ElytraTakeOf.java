package com.bt.BThack.impl.Module.PLAYER.ElytraTakeOf;

import com.bt.BThack.api.Module.Module;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ElytraTakeOf extends Module {

    public ElytraTakeOf() {
        super("ElytraTakeOf",
                "Removes elytra from the player when turned on and puts it on if the player falls. After it turns off.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addSlider("Fall Distance", this, 4, 1, 7, true);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        Item armor = null;
        byte a = 0;
        for (ItemStack stack : mc.player.getEquipmentAndArmor()) {
            if (a == 4) {
                armor = stack.getItem();
                break;
            } else
                a++;
        }

        if (mc.player.collidedVertically) {
            if (!mc.player.isElytraFlying()) {
                if (!(armor instanceof ItemArmor)) {
                    for (int needSlot = 0; needSlot < 36; needSlot++) {
                        if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemArmor) {
                            int item;
                            if (needSlot < 9)
                                item = needSlot + 36;
                            else
                                item = needSlot;

                            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.windowClick(0, item, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.updateController();
                            break;
                        }
                    }
                }
            }
        } else {
            if (mc.player.fallDistance > getSlider(this.name, "Fall Distance")) {
                if (!(armor instanceof  ItemElytra)) {
                    for (int needSlot = 0; needSlot < 36; needSlot++) {
                        if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemElytra) {
                            int item;
                            if (needSlot < 9)
                                item = needSlot + 36;
                            else
                                item = needSlot;

                            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.windowClick(0, item, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.updateController();

                            new ElytraTakeOfActivateElytraThread().start();
                            mc.player.connection.sendPacket(new CPacketPlayer(true));
                            toggle();
                            break;
                        }
                    }
                } else {
                    if (!mc.player.isElytraFlying()) {
                        new ElytraTakeOfActivateElytraThread().start();
                        toggle();
                    }
                }
            }
        }
    }
}

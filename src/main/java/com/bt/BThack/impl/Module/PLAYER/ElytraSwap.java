package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class ElytraSwap extends Module {

    public ElytraSwap() {
        super("ElytraSwap",
                "When enabled, replaces eliters with breastplate, and also in reverse.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        //addCheckbox("Find Best Chestplate", this, true);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        boolean finded = false;
        Item armor = null;
        byte a = 0;
        for (ItemStack stack : mc.player.getEquipmentAndArmor()) {
            if (a == 4) {
                armor = stack.getItem();
                break;
            }
            else
                a++;
        }
        if (armor instanceof ItemElytra) {
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
                    finded = true;
                    toggle();
                    break;
                }
            }
        } else {
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
                    finded = true;
                    toggle();
                    break;
                }
            }
        }
        if (!finded) {
            toggle();
        }
    }
}

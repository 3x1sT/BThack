package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ItemsUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ElytraReplace extends Module {

    public ElytraReplace() {
        super("ElytraReplace",
                "Automatically replaces elytra if their durability has reached the minimum limit.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addSlider("Min Durability", this, 15, 1, 100, true);
    }

    boolean needReplace = true;  //Protection against those moments when several different elytres can be dressed at the same time.

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        ItemStack stack = mc.player.inventory.armorItemInSlot(2);

        if (stack.getItem() == Items.ELYTRA) {
            if (ItemsUtil.getItemDurability(stack) < getSlider(this.name, "Min Durability")) {
                if (!needReplace) return;
                for (int i = 0; i < 36; i++) {
                    ItemStack item = mc.player.inventory.getStackInSlot(i);

                    if (item.getItem() == Items.ELYTRA) {
                        int durability = ItemsUtil.getItemDurability(item);

                        if (durability > getSlider(this.name, "Min Durability")) {
                            int needItem = i;

                            if (needItem < 9) {
                                needItem = needItem + 36;
                            }

                            ItemsUtil.replaceItems(6, needItem);
                            needReplace = false;
                        }
                    }
                }
            } else {
                needReplace = true;
            }
        }
    }
}

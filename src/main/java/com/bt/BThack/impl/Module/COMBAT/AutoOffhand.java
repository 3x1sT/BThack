package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class AutoOffhand extends Module {

    public AutoOffhand() {
        super("AutoOffhand",
                "Automatically places a need item in your offhand",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        addMode("Mode", this, new ArrayList<>(Arrays.asList("Standard", "Extra")));

        //Standard Settings
        addMode("Mode", "Standard", "Item", this, new ArrayList<>(Arrays.asList("Totem", "Crystal", "Gapple")));
        //////

        //Extra Settings
        addCheckbox("Mode", "Extra", "Totem", this, true);
        addSlider("Totem", true, "Totem MaxHP", this, 12, 1, 20, false);
        addSlider("Totem", true, "Totem MinHP", this, 0, 0, 19, false);

        addCheckbox("Mode", "Extra", "Crystal", this, false);
        addSlider("Crystal", true, "Crystal MaxHP", this, 1, 1, 20, false);
        addSlider("Crystal", true, "Crystal MinHP", this, 0, 0, 19, false);

        addCheckbox("Mode", "Extra", "Gapple", this, true);
        addSlider("Gapple", true, "Gapple MaxHP", this, 20, 1, 20, false);
        addSlider("Gapple", true, "Gapple MinHP", this, 12, 0, 19, false);
        //////

        addCheckbox("Replace Other", this, true);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        switch (getMode(this.name, "Mode")) {
            case "Standard":
                arrayListInfo = "Standard";
                switch (getMode(this.name, "Item")) {
                    case "Totem":
                        tryOffhand(Items.TOTEM_OF_UNDYING);
                        break;
                    case "Crystal":
                        tryOffhand(Items.END_CRYSTAL);
                        break;
                    case "Gapple":
                        tryOffhand(Items.GOLDEN_APPLE);
                        break;
                }
                break;
            case "Extra":
                arrayListInfo = "Extra";
                float playerHp = mc.player.getHealth();
                if (getCheckbox(this.name, "Totem")) {
                    if (playerHp > getSlider(this.name, "Totem MinHP") && playerHp < getSlider(this.name, "Totem MaxHP")) {
                        if (tryOffhand(Items.TOTEM_OF_UNDYING))
                            return;
                    }
                }
                if (getCheckbox(this.name, "Crystal")) {
                    if (playerHp > getSlider(this.name, "Crystal MinHP") && playerHp < getSlider(this.name, "Crystal MaxHP")) {
                        if (tryOffhand(Items.END_CRYSTAL))
                            return;
                    }
                }
                if (getCheckbox(this.name, "Gapple")) {
                    if (playerHp > getSlider(this.name, "Gapple MinHP") && playerHp < getSlider(this.name, "Gapple MaxHP")) {
                        if (tryOffhand(Items.GOLDEN_APPLE))
                            return;
                    }
                }
                break;
        }
    }

    //It works weird sometimes, but I don't know how to fix it. Just take it for what it is :/
    //In any case, this problem is in many cheats.
    public static void releaseHand() {
        Minecraft mc = Minecraft.getMinecraft();

        int freeSlot = InventoryUtils.findItem(Items.AIR);
        if (freeSlot < 9) freeSlot += 36;

        mc.playerController.windowClick(0, freeSlot, 1, ClickType.PICKUP_ALL, mc.player);
        mc.playerController.updateController();
    }

    private boolean tryOffhand(Item item) {
        if (mc.player.getHeldItemOffhand().getItem() != item) {
            if (getCheckbox(this.name, "Replace Other")) {
                if (mc.player.getHeldItemOffhand().getItem() != Items.AIR)
                    return false;
            }

            int slot = InventoryUtils.findItem(item);
            if (slot == -1) return false;
            if (slot < 9) slot += 36;

            if (mc.player.inventory.getItemStack().getItem() != Items.AIR)
                releaseHand();

            InventoryUtils.replaceItems(slot, InventoryUtils.OFFHAND_SLOT, 0);

            /*
            mc.playerController.windowClick(0, slot, 1, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
            mc.playerController.windowClick(0, InventoryUtils.OFFHAND_SLOT, 1, ClickType.PICKUP_ALL, mc.player);
            mc.playerController.updateController();
            mc.playerController.windowClick(0, slot, 1, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();

             */
            return true;
        }
        return false;
    }
}

package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.InventoryUtils;
import com.bt.BThack.api.Utils.WorldUtils;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoTotemFall extends Module {

    public AutoTotemFall() {
        super("AutoTotemFall",
                "Places a totem if you've almost landed on the ground.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        addSlider("Start Fall Check", this, 10, 5, 20, true);
        addSlider("Height To Ground", this, 5, 3, 10, true);
    }

    boolean isFalling = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        if (mc.player.fallDistance > getSlider(this.name, "Start Fall Check")) {
            if (!mc.player.collidedVertically)
                isFalling = true;
        }

        if (WorldUtils.getGroundPos(mc.world, mc.player).y + getSlider(this.name, "Height To Ground") > mc.player.posY) {
            if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                if (mc.player.inventory.getItemStack().getItem() != Items.AIR) {
                    AutoOffhand.releaseHand();
                }

                int slot = InventoryUtils.findItem(Items.TOTEM_OF_UNDYING);
                if (slot == -1) return;

                if (slot < 9) slot += 36;

                InventoryUtils.replaceItems(slot, InventoryUtils.OFFHAND_SLOT, 5);
            }
        }
    }
}

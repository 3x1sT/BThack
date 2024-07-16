package com.bt.BThack.impl.Module.PLAYER.ChestStealer;

import com.bt.BThack.api.Module.Module;
import net.minecraft.inventory.ContainerChest;
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
                new ChestStealerThread().start();
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

package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FastFall extends Module {
    public FastFall() {
        super("FastFall",
                "The player falls faster.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Activate Distance", this, 1,0.1,5,false);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            double f = getSlider(this.name, "Activate Distance");
            if (mc.player.fallDistance > f) {
                mc.player.posY = mc.player.posY - mc.player.fallDistance;
            }
        }
    }
}

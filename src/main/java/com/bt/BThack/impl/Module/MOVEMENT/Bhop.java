package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Bhop extends Module {
    public Bhop() {
        super("Bhop",
                "Bhop:/",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (!mc.player.isInWater() && !mc.player.isInLava() && mc.player.onGround) {
                mc.player.jump();
            }
        }
    }
}

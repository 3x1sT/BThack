package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Glide extends Module {
    public Glide() {
        super("Glide",
                "Makes the fall slow and steady.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Speed", this, 0.125,0.05,0.5,false);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            double s = getSlider(this.name, "Speed");
            double speed = -s;
            if (mc.player.fallDistance != 0 && mc.player.motionY != 0) {
                mc.player.motionY = speed;
            }
        }
    }
}

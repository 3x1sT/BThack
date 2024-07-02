package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Objects;

public class AirJump extends Module {
    public AirJump() {
        super("AirJump",
                "The player can jump through the air.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("Default");
        options.add("OnGround");

        addMode("Mode", this, options);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (!mc.player.isInWater() && !mc.player.isInLava()) {
                String Mode = getMode(this.name, "Mode");
                if (Objects.equals(Mode, "Default")) {
                    if (mc.gameSettings.keyBindJump.isPressed()) {
                        mc.player.jump();
                    }
                } else {
                    mc.player.onGround = true;
                }
            }
        }
    }
}

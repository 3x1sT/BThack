package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint",
                "The player always runs.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (!mc.player.isSneaking() && mc.player.movementInput.moveForward > 0 && !mc.player.collidedHorizontally && !mc.player.isSprinting()) {
                mc.player.setSprinting(true);
            }
        }
    }
}

package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class NoJumpDelay extends Module {
    public NoJumpDelay() {
        super("NoDelay",
                "Disable jump delay.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        mc.player.jumpTicks = 0;
    }
}

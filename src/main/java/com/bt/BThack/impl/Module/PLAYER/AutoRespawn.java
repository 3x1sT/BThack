package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn",
                "Automatically respawn the player.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (mc.player.getHealth() <= 0 && mc.player.isDead && mc.currentScreen instanceof GuiGameOver) {
                mc.player.respawnPlayer();
                mc.displayGuiScreen(null);
            }
        }
    }
}

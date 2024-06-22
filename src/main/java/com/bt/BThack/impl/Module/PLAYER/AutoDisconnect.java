package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoDisconnect extends Module {
    public AutoDisconnect() {
        super("AutoDisconnect",
                "Disconnect a player if his health is below the limit.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addSlider("Min Health", this, 10,1,20,true);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (mc.player.getHealth() <= getSlider(this.name, "Min Health")) {
                mc.player.connection.handleDisconnect(new SPacketDisconnect());
            }
        }
    }
}

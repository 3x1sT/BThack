package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class NoSwing extends Module {
    public NoSwing() {
        super("NoSwing",
                "Cancels server or client swing animation.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );
        ArrayList<String> options = new ArrayList<>();

        options.add("Client");
        options.add("Server");

        addMode("Mode", this, options, "Mode");
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send e) {
        if (getMode(this.name, "Mode").equals("Server")  && e.getPacket() instanceof CPacketAnimation) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        mc.player.isSwingInProgress = false;
        mc.player.swingProgressInt = 0;
        mc.player.swingProgress = 0.0f;
        mc.player.prevSwingProgress = 0.0f;
    }
}

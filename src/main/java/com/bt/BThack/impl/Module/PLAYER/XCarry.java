package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class XCarry extends Module {
    public XCarry() {
        super("XCarry",
                "Allows you to save items in crafting slots.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );
    }

    @SubscribeEvent
    public void onSendPacket(PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketCloseWindow)
        {
            final CPacketCloseWindow packet = (CPacketCloseWindow) e.getPacket();
            if (packet.windowId == mc.player.inventoryContainer.windowId)
            {
                e.setCanceled(true);
            }
        }
    }
}

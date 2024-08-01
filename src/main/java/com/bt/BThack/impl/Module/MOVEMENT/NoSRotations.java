package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class NoSRotations extends Module {
    public NoSRotations() {
        super("NoSRotations",
                "Blocks camera rotations caused by the server.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive e) {
        if (nullCheck()) return;
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
            packet.yaw = mc.player.rotationYaw;
            packet.pitch = mc.player.rotationPitch;
        }

    }
}

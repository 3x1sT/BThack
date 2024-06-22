package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall",
                "Protects you from falling.",
                Keyboard.KEY_NONE,
                Module.Category.MOVEMENT,
                false
        );
    }

    public void onClientTick(ClientTickEvent e) {
        if (!nullCheck()) {
            if (!mc.player.onGround) {
                double oldX = mc.player.posX;
                double oldY = mc.player.posY;
                double oldZ = mc.player.posZ;

                if (mc.player.fallDistance < 2.5f) {
                    return;
                }

                for (int i = 0; i < 5; i++) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(oldX, oldY, oldZ, false));
                }

                mc.player.setPositionAndUpdate(oldX, oldY, oldZ);

                for (int i = 0; i < 5; i++) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(oldX, oldY, oldZ, false));
                }

            }

        }
    }
}
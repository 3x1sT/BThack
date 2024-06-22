package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.mixins.mixin.AccessorEntity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

/**
 * @see com.bt.BThack.mixins.mixin.AccessorEntity
 */

public class ElytraFastClose extends Module {
    public ElytraFastClose() {
        super(
                "ElytraFastClose",
                "Closes elytra on ground without waiting for the server.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addCheckbox("Stop Motion", this, true);
        addSlider("Y Distance", this, 0.05, 0.0, 1.0, false);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.START || !mc.player.isElytraFlying()) return;

        if (mc.world.collidesWithAnyBlock(mc.player.getEntityBoundingBox().offset(0.0, -getSlider(this.name, "Y Distance"), 0.0))) {
            if (getCheckbox(this.name, "Stop Motion")) {
                mc.player.motionX = 0.0;
                mc.player.motionY = 0.0;
                mc.player.motionZ = 0.0;
            }
            ((AccessorEntity)mc.player).invokeSetFlag(7, false);
            mc.player.connection.sendPacket(new CPacketPlayer(true));
        }
    }
}

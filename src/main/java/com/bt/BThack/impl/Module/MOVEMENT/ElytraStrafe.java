package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Modules.StrafeUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ElytraStrafe extends Module {

    public ElytraStrafe() {
        super("ElytraStrafe",
                "Allows you to rotate on eliters without losing speed. Ideal for maintaining speed when traveling on Nether.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (mc.player.isElytraFlying()) {
                double currentPlayerSpeed = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
                double yaw = Math.toRadians(StrafeUtils.getPlayerDirection());
                mc.player.motionZ = Math.cos(yaw) * currentPlayerSpeed;
                mc.player.motionX = -Math.sin(yaw) * currentPlayerSpeed;
            }
        }
    }
}

package com.bt.BThack.impl.Module.PLAYER.ElytraTakeOf;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.init.SoundEvents;

public class ElytraTakeOfActivateElytraThread extends Thread implements Mc {

    @Override
    public void run() {

        while (!mc.player.isElytraFlying()) {
            mc.gameSettings.keyBindJump.pressed = true;

            try {
                sleep(100);
            } catch (InterruptedException e) {}
            mc.gameSettings.keyBindJump.pressed = false;

            if (mc.player.collidedVertically)
                stop();

            if (mc.player.isElytraFlying())
                mc.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        }
    }
}
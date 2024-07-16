package com.bt.BThack.impl.Module.MOVEMENT.AutoJump;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.Interfaces.Mc;

public class AutoJumpThread extends Thread implements Mc {
    public void run() {
        while (Client.getModuleByName("AutoJump").isEnabled()) {
            if (!mc.gameSettings.keyBindJump.pressed) {
                mc.gameSettings.keyBindJump.pressed = true;
            }

            try {
                sleep(50);
            } catch (InterruptedException ignored) {}
        }

        mc.gameSettings.keyBindJump.pressed = false;
    }
}

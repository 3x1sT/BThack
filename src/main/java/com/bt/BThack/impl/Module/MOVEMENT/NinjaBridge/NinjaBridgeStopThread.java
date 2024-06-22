package com.bt.BThack.impl.Module.MOVEMENT.NinjaBridge;

import com.bt.BThack.api.Utils.Interfaces.Mc;

public class NinjaBridgeStopThread extends Thread implements Mc {
    public void run() {
        try {
            sleep(50);
        } catch (InterruptedException ignored) {}
        mc.gameSettings.keyBindBack.pressed = false;
        mc.gameSettings.keyBindRight.pressed = false;
        mc.gameSettings.keyBindUseItem.pressed = false;
    }
}

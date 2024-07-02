package com.bt.BThack.impl.Module.MOVEMENT.ShiftSpam;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;

public class ShiftSpamThread extends Thread implements Mc {
    public void run() {
        while (Client.getModuleByName("ShiftSpam").isEnabled()) {
            long delayActive = (long) (Module.getSlider("ShiftSpam", "Delay Active") * 1000);
            long delayDeActive = (long) (Module.getSlider("ShiftSpam", "Delay deActive") * 1000);

            mc.gameSettings.keyBindSneak.pressed = true;
            try {
                ShiftSpamThread.sleep(delayActive);
            } catch (InterruptedException ignored) {}
            mc.gameSettings.keyBindSneak.pressed = false;
            try {
                ShiftSpamThread.sleep(delayDeActive);
            } catch (InterruptedException ignored) {}
        }
    }
}

package com.bt.BThack.impl.Module.MOVEMENT.CameraRotator;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;

public class CameraRotatorThread extends Thread implements Mc {
    public static double a = 0;
    public static float b = 0;
    public static boolean inversion;
    public void run() {
        while (Client.getModuleByName("CameraRotator").isEnabled()) {
            if (mc.player == null || mc.world == null) return;
            a = Module.getSlider("CameraRotator", "Speed");
            inversion = Module.getCheckbox("CameraRotator", "Inversion");
            b = (float) (1080 * a);
            b = b / 950;
            if (!inversion) {
                mc.player.rotationYaw = mc.player.rotationYaw + b;
            } else {
                mc.player.rotationYaw = mc.player.rotationYaw - b;
            }
            try {
                sleep(1);
            } catch (InterruptedException ignored) {}
        }
    }
}

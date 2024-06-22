package com.bt.BThack.api.Utils;

import com.bt.BThack.Client;
import com.bt.BThack.api.Utils.Interfaces.Mc;

public class SpeedMathThread extends Thread implements Mc {

    public static double speed = 0;
    public static boolean active = false;

    public void run() {
        active = true;
        while (Client.getModuleByName("HUD").isEnabled()) {
            if (mc.player != null && mc.world != null) {
                double oldX = mc.player.posX;
                double oldZ = mc.player.posZ;

                try {
                    sleep(200);
                } catch (InterruptedException ignored) {
                }

                if (mc.player != null && mc.world != null) {

                    double newX = mc.player.posX;
                    double newZ = mc.player.posZ;

                    double sX = Math.pow(newX - oldX, 2);
                    double sZ = Math.pow(newZ - oldZ, 2);

                    speed = (Math.sqrt(sX + sZ)) * 5;
                }
            } else {
                break;
            }
        }
        active = false;
        speed = 0;
    }
}

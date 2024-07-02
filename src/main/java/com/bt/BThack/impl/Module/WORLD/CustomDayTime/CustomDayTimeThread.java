package com.bt.BThack.impl.Module.WORLD.CustomDayTime;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;

import java.util.Objects;

public class CustomDayTimeThread extends Thread implements Mc {
    public void run() {
        while (Client.getModuleByName("CustomDayTime").isEnabled()) {
            if (mc.player != null && mc.world != null) {
                String Mode = Module.getMode("CustomDayTime", "Mode");

                if (Objects.equals(Mode, "Day")) {
                    CustomDayTime.time = 5000L;
                }
                if (Objects.equals(Mode, "Night")) {
                    CustomDayTime.time = 17000L;
                }
                if (Objects.equals(Mode, "Morning")) {
                    CustomDayTime.time = 0L;
                }
                if (Objects.equals(Mode, "Sunset")) {
                    CustomDayTime.time = 13000L;
                }
                if (Objects.equals(Mode, "Custom")) {
                    CustomDayTime.time = (long) Module.getSlider("CustomDayTime", "Custom Time");
                }
                if (Objects.equals(Mode, "Spin")) {
                    double speed = Module.getSlider("CustomDayTime", "Spin speed");
                    float speedFactor = (float) Module.getSlider("CustomDayTime", "Extra Spin");

                    long newTime = (long) (CustomDayTime.time + ((speed * speedFactor) / 50));
                    if (newTime >= 24000L) newTime = 0L;
                    CustomDayTime.time = newTime;
                    try {
                        sleep(20);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    try {
                        sleep(200);
                    } catch (InterruptedException ignored) {
                    }
                }
            } else {
                try {
                    sleep(200);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}

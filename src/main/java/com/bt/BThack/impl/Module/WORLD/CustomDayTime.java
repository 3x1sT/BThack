package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Objects;

public class CustomDayTime extends Module {
    public static long time = 0L;

    public CustomDayTime() {
        super("CustomDayTime",
                "Sets custom day time.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("Day");
        options.add("Night");
        options.add("Morning");
        options.add("Sunset");
        options.add("Spin");
        options.add("Custom");

        addMode("Mode", this, options);
        addSlider("Mode", "Custom","Custom Time", this, 10000, 1, 24000, true);
        addSlider("Mode", "Spin","Spin speed", this, 500,250,2500,true);
        addSlider("Mode", "Spin","Extra Spin", this, 1, 1, 2.5, false);
    }

    @Override
    public void onEnable() {
        ThreadManager.startNewThread(thread -> {
            while (Client.getModuleByName("CustomDayTime").isEnabled()) {
                if (mc.player != null && mc.world != null) {
                    String mode = Module.getMode("CustomDayTime", "Mode");

                    Client.getModuleByName("CustomDayTime").arrayListInfo = mode;

                    if (Objects.equals(mode, "Day")) {
                        CustomDayTime.time = 5000L;
                    }
                    if (Objects.equals(mode, "Night")) {
                        CustomDayTime.time = 17000L;
                    }
                    if (Objects.equals(mode, "Morning")) {
                        CustomDayTime.time = 0L;
                    }
                    if (Objects.equals(mode, "Sunset")) {
                        CustomDayTime.time = 13000L;
                    }
                    if (Objects.equals(mode, "Custom")) {
                        CustomDayTime.time = (long) Module.getSlider("CustomDayTime", "Custom Time");
                    }
                    if (Objects.equals(mode, "Spin")) {
                        double speed = Module.getSlider("CustomDayTime", "Spin speed");
                        float speedFactor = (float) Module.getSlider("CustomDayTime", "Extra Spin");

                        long newTime = (long) (CustomDayTime.time + ((speed * speedFactor) / 50));
                        if (newTime >= 24000L) newTime = 0L;
                        CustomDayTime.time = newTime;
                        try {
                            thread.sleep(20);
                        } catch (InterruptedException ignored) {
                        }
                    } else {
                        try {
                            thread.sleep(200);
                        } catch (InterruptedException ignored) {
                        }
                    }
                } else {
                    try {
                        thread.sleep(200);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        });
    }
}

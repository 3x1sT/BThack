package com.bt.BThack.impl.Module.WORLD.CustomDayTime;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

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
        new CustomDayTimeThread().start();
    }
}

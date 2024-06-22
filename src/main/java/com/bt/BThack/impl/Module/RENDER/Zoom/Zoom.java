package com.bt.BThack.impl.Module.RENDER.Zoom;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class Zoom extends Module {
    public static boolean active = false;
    public static float a;
    public Zoom() {
        super("Zoom",
                "Reduces camera FOV.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Zoom Strength", this, 1,0.1,7,false);
        addCheckbox("Smooth Camera", this, true);
        addCheckbox("Hide HUD", this, true);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        active = true;
        a = mc.gameSettings.fovSetting;
        new ZoomThread().start();
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        active = false;
    }
}

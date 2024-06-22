package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {
    public static float a;
    public FullBright() {
        super("FullBright",
                "Increases gamma settings.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        a = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 1000;
        
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        mc.gameSettings.gammaSetting = a;
    }
}

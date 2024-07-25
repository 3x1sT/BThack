package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {
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
        mc.gameSettings.gammaSetting = 1000;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1f;
    }
}

package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class ModuleOnOffSound extends Module {
    public ModuleOnOffSound() {
        super("ModuleOnOffSound",
                "You will have a sound playing when you turn the module off/on.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                false
        );

        allowRemapKeyCode = false;

        addSlider("Volume", this, 1, 1, 0.25, false);
    }
}

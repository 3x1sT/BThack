package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class MemoryCleaner extends Module {
    public MemoryCleaner() {
        super("MemoryCleaner",
                "Enabling and configuring the memory cleaning module.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                true
        );

        allowRemapKeyCode = false;

        addCheckbox("Show Messages", this, true);
        addCheckbox("Clean On Join", this, true);
        addCheckbox("Clean On Init", this, true);
        addSlider("Force Clean %", this, 80,0,100,true);
        addCheckbox("Auto Cleanup", this, true);
        addSlider("Min Interval",this, 300, 30, 500,true);
        addSlider("Max Interval", this, 1200, 500, 3000, true);
        addSlider("MinAFKTime", this, 30, 5, 240, true);
    }
}

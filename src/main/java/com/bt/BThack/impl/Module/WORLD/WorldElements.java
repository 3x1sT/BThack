package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class WorldElements extends Module {
    public WorldElements() {
        super("WorldElements",
                "A module for fine-tuning the world.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        addSlider("Star Brightness", this, 0.5,0,1, false);
        addCheckbox("Change Moon Phase", this, false);
        addSlider("Change Moon Phase", true,"Moon Phase", this, 5, 1, 8, true);
    }
}

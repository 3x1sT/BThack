package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class FogColor extends Module {
    public FogColor() {
        super("FogColor",
                "Changes the color of the fog.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        addSlider("Rainbow", false,"Red", this, 255, 0, 255, false);
        addSlider("Rainbow", false,"Green", this, 255, 0, 255, false);
        addSlider("Rainbow", false,"Blue", this, 255, 0, 255, false);
        addCheckbox("Rainbow", this, false);
    }
}

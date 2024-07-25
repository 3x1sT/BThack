package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class CloudsColor extends Module {
    public CloudsColor() {
        super("CloudsColor",
                "Changes the color of the clouds.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        addSlider("Red", this, 255, 0, 255, false);
        addSlider("Green", this, 255, 0, 255, false);
        addSlider("Blue", this, 255, 0, 255, false);
    }
}

package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

/**
 * @see com.bt.BThack.mixins.mixin.MixinWorld
 */

public class SkyColour extends Module {
    public SkyColour() {
        super("SkyColour",
                "Changes the color of the sky in the world.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        addSlider("Red", this, 21, 0, 255, true);
        addSlider("Green", this, 191, 0, 255, true);
        addSlider("Blue", this, 219, 0, 255, true);
    }
}

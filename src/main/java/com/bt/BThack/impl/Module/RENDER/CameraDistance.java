package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class CameraDistance extends Module {

    public CameraDistance() {
        super("CameraDistance",
                "Changes the maximum range of the third-person camera.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Distance", this, 7, 2.5, 40, false);
    }
}

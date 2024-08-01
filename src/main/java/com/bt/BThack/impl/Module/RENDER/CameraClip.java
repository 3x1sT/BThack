package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class CameraClip extends Module {

    public CameraClip() {
        super("CameraClip",
                "Allows your camera to clip through blocks",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );
    }
}

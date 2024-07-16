package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class NoFog extends Module {
    public NoFog() {
        super("NoFog",
                "Disables fog.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );
    }
}

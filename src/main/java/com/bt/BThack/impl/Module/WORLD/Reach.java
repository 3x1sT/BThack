package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class Reach extends Module {
    public Reach() {
        super("Reach",
                "Increases the player's reach range.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        addSlider("Range", this, 0.5,0.1,4,false);
    }
}

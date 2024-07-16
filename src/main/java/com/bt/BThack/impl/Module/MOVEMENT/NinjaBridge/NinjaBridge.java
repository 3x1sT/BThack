package com.bt.BThack.impl.Module.MOVEMENT.NinjaBridge;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class NinjaBridge extends Module {
    public NinjaBridge() {
        super("NinjaBridge",
                "Automatically builds a NinjaBridge-style bridge in the direction you're facing.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Action delay", this, 50, 0, 200, true);
        addSlider("Place block time", this, 175, 50, 300, true);
        addSlider("Place block factor", this, 25, 1, 100, true);
        addSlider("Extra air check",this, 0.03, 0.0, 0.2, false);
    }

    @Override
    public void onEnable() {
        NinjaBridgeThread.close = false;
        new NinjaBridgeThread().start();
    }
}

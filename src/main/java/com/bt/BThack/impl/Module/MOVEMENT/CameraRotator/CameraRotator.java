package com.bt.BThack.impl.Module.MOVEMENT.CameraRotator;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class CameraRotator extends Module {
    public CameraRotator() {
        super("CameraRotator",
                "Rotates the camera:/",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Speed", this, 1,0.1,4,false);
        addCheckbox("Inversion", this, false);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;
        new CameraRotatorThread().start();
    }
}

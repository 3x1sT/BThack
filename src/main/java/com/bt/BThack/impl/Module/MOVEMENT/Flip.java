package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class Flip extends Module {
    public Flip() {
        super("Flip",
                "Rotates the player 180 degrees.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addCheckbox("Safe Speed", this, true);
    }

    @Override
    public void onEnable() {
        float mX = (float)mc.player.motionX;
        float mZ = (float)mc.player.motionZ;
        mc.player.rotationYaw -= 180;
        if (getCheckbox(this.name, "Safe Speed")) {
            mc.player.motionX = -mX;
            mc.player.motionZ = -mZ;
        }
        toggle();
    }
}

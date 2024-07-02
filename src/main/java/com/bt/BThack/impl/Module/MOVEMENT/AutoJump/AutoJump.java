package com.bt.BThack.impl.Module.MOVEMENT.AutoJump;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class AutoJump extends Module {
    public AutoJump() {
        super("AutoJump",
                "Automatic jumps.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        new AutoJumpThread().start();
    }
}

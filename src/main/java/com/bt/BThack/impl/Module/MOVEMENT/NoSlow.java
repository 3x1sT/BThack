package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

/**
 * @see com.bt.BThack.mixins.mixin.MixinBlockSoulSand
 * @see com.bt.BThack.mixins.mixin.MixinBlockSlime
 */

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow",
                "Prevents the player from slowing down.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addCheckbox("SoulSand", this, true);
        addCheckbox("Slime Landed", this, true);
        addCheckbox("Instant Stop", this, true);
    }
}

package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

/**
 * @see com.bt.BThack.mixins.mixin.MixinEntityRenderer
 */

public class NoEntityTrace extends Module {
    public NoEntityTrace() {
        super("NoEntityTrace",
                "Yau can interact through entities.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        addCheckbox("Only Pickaxe", this, false);
    }
}

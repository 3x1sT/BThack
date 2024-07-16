package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class ChildModel extends Module {

    public ChildModel() {
        super("ChildModel",
                "Changes your model to a child's model.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );
    }
}

package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class GuiMove extends Module {
    public GuiMove() {
        super("GuiMove",
                "Allows the player to move when the GUI is turned on.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );
    }
}

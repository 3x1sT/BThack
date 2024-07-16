package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class BThackMainMenu extends Module {

    public BThackMainMenu() {
        super("BThackMainMenu",
                "Replaces the classic main menu of minecraft with the main menu from BThack.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                false
        );
    }
}

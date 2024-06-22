package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class InventoryMove extends Module {
    public InventoryMove() {
        super("InventoryMove",
                "Allows the player to move when the GUI is turned on.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Rotate Speed", this, 5,0,20, true);
        addCheckbox("Item Move Bypass", this, true);
    }
}

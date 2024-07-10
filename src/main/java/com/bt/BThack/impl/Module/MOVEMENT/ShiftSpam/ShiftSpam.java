package com.bt.BThack.impl.Module.MOVEMENT.ShiftSpam;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class ShiftSpam extends Module{
    public ShiftSpam() {
        super("ShiftSpam",
                "Spam with shift:/",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Delay Active", this, 0.1,0.01,1,false);
        addSlider("Delay deActive", this, 0.1,0.01,1,false);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        new ShiftSpamThread().start();
    }
}

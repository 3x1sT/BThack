package com.bt.BThack.impl.Module.PLAYER.Spammer;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Spammer extends Module {
    public static int active = 1;
    public Spammer() {
        super("Spammer",
                "Automatically writes text to chat.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("InOrder");
        options.add("Random");

        addMode("Mode", this, options, "Mode");
        addSlider("Delay(Second)", this, 60,1,300,true);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        new SendMessage().start();
        active = 1;
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        active = 0;
    }
}

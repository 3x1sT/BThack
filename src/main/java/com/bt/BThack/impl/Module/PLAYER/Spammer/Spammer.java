package com.bt.BThack.impl.Module.PLAYER.Spammer;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Spammer extends Module {
    protected static ReadTXT readTXT = new ReadTXT();

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

        addMode("Mode", this, options);
        addSlider("Delay(Second)", this, 60,1,600,true);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        new SendMessage().start();
    }
}

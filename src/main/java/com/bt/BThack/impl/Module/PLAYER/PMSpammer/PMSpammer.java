package com.bt.BThack.impl.Module.PLAYER.PMSpammer;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Module.PLAYER.Spammer.ReadTXT;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class PMSpammer extends Module {
    protected static ReadTXT readTXT = new ReadTXT();

    public PMSpammer() {
        super("PMSpammer",
                "Advanced spammer for sending private messages to all players on the server.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );


        ArrayList<String> options = new ArrayList<>();

        options.add("InOrder");
        options.add("Random");

        addMode("Mode", this, options);
        addSlider("Delay(Second)", this, 45,15,600,true);
        addCheckbox("Delay Spread", this, false);
        addSlider("Delay Spread", true, "Spread range", this, 0.3, 0.1, 0.7, false);

        addCheckbox("Anti Spam Fix", this, false);
        addCheckbox("LowerCase", this, false);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        new PMSpammerThread().start();
    }
}

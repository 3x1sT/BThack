package com.bt.BThack.impl.Module.PLAYER.ActionBot;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class ActionBot extends Module {
    ActionBotRunTimeThread thread;

    public ActionBot() {
        super("ActionBot",
                "Can execute various given commands in alternating order.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addCheckbox("Repeat", this, false);


        addOpenGuiButton("Open Config", this, null);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        super.onEnable();

        thread = new ActionBotRunTimeThread();
        thread.start();
    }

    @Override
    public void onDisable() {
        if (nullCheck() || thread == null) {
            super.onDisable();
        }

        try {
            thread.stop();
        } catch (Exception ignored) {}
    }
}

package com.bt.BThack.impl.Module.MISC;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class GameCrasher extends Module {
    public GameCrasher() {
        super("GameCrasher",
                "Crashing your Minecraft, lol.",
                Keyboard.KEY_NONE,
                Category.MISC,
                false
        );
    }

    @Override
    public void onEnable() {
        this.setToggled(false);
        throw new RuntimeException("Your game is crashed using the BThack module \"GameCrasher\".");
    }
}

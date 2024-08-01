package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.RusherHackCheck;
import org.lwjgl.input.Keyboard;

public class BThackMainMenu extends Module {

    public BThackMainMenu() {
        super("BThackMainMenu",
                "Replaces the classic main menu of minecraft with the main menu from BThack.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                true
        );
    }

    @Override
    public void onEnable() {
        if (RusherHackCheck.isRusherHack) {
            if (!nullCheck()) {
                ChatUtils.sendMessage("You can not enable BThackMainMenu module, because it is not compatible with RusherHack cheat client.");
            }
            setToggled(false);
        }
    }
}

package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.impl.CustomGui.Gui.BThackSettingsGui;
import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class Settings extends Module {
    public Settings() {
        super("Settings",
                "Opens BThack settings.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                false
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        mc.displayGuiScreen(new BThackSettingsGui());
        toggle();
    }
}

package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.CustomGui.Gui.Client.HudMover.HudMoverGui;
import org.lwjgl.input.Keyboard;

public class HudEditor extends Module {

    public HudEditor() {
        super("HudEditor",
                "Opens the HUD editor menu.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                false
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        mc.displayGuiScreen(new HudMoverGui());
        toggle();
    }
}

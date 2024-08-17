package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.DiscordUtil;
import org.lwjgl.input.Keyboard;

public class DiscordRPC extends Module {

    public DiscordRPC() {
        super("DiscordRPC",
                "You get BThack activity",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                true
        );
        allowRemapKeyCode = false;

        addCheckbox("Secret :3", this, false);
    }

    @Override
    public void onEnable() {
        DiscordUtil.startup();
    }

    @Override
    public void onDisable() {
        DiscordUtil.shutdown();
    }
}

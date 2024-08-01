package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class AutoJump extends Module {
    public AutoJump() {
        super("AutoJump",
                "Automatic jumps.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        ThreadManager.startNewThread(thread -> {
            while (Client.getModuleByName("AutoJump").isEnabled()) {
                if (!mc.gameSettings.keyBindJump.pressed) {
                    mc.gameSettings.keyBindJump.pressed = true;
                }

                try {
                    thread.sleep(50);
                } catch (InterruptedException ignored) {}
            }

            mc.gameSettings.keyBindJump.pressed = false;
        });
    }
}

package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class ShiftSpam extends Module{
    public ShiftSpam() {
        super("ShiftSpam",
                "Spam with shift:/",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Delay Active", this, 0.1,0.01,1,false);
        addSlider("Delay deActive", this, 0.1,0.01,1,false);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        ThreadManager.startNewThread(thread -> {
            while (Client.getModuleByName("ShiftSpam").isEnabled()) {
                long delayActive = (long) (Module.getSlider("ShiftSpam", "Delay Active") * 1000);
                long delayDeActive = (long) (Module.getSlider("ShiftSpam", "Delay deActive") * 1000);

                mc.gameSettings.keyBindSneak.pressed = true;
                try {
                    thread.sleep(delayActive);
                } catch (InterruptedException ignored) {}
                mc.gameSettings.keyBindSneak.pressed = false;
                try {
                    thread.sleep(delayDeActive);
                } catch (InterruptedException ignored) {}
            }
        });
    }
}

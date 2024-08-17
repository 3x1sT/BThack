package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Managers.Thread.IThread;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class CameraRotator extends Module {
    public CameraRotator() {
        super("CameraRotator",
                "Rotates the camera:/",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Speed", this, 1,0.1,4,false);
        addCheckbox("Inversion", this, false);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;
        ThreadManager.startNewThread(new IThread() {
            double a = 0;
            float b = 0;
            boolean inversion;

            @Override
            public void start(Thread thread) {
                while (Client.getModuleByName("CameraRotator").isEnabled()) {
                    if (mc.player == null || mc.world == null) return;
                    a = Module.getSlider("CameraRotator", "Speed");
                    inversion = Module.getCheckbox("CameraRotator", "Inversion");
                    b = (float) (1080 * a);
                    b = b / 950;
                    if (!inversion) {
                        mc.player.rotationYaw = mc.player.rotationYaw + b;
                    } else {
                        mc.player.rotationYaw = mc.player.rotationYaw - b;
                    }
                    try {
                        thread.sleep(1);
                    } catch (InterruptedException ignored) {}
                }
            }
        });
    }
}

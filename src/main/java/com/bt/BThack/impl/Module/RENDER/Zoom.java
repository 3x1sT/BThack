package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Managers.Thread.IThread;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

public class Zoom extends Module {
    public static boolean active = false;
    public static float a;
    public Zoom() {
        super("Zoom",
                "Reduces camera FOV.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Zoom Strength", this, 1,0.1,7,false);
        addCheckbox("Smooth Camera", this, true);
        addCheckbox("Hide HUD", this, true);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        active = true;
        a = mc.gameSettings.fovSetting;
        ThreadManager.startNewThread(new IThread() {
            float d;
            boolean smoothCam;
            boolean hideHUD;

            @Override
            public void start(Thread thread) {
                while (Zoom.active) {
                    float a = Zoom.a;
                    double strength = Module.getSlider("Zoom", "Zoom Strength");
                    smoothCam = Module.getCheckbox("Zoom", "Smooth Camera");
                    hideHUD = Module.getCheckbox("Zoom", "Hide HUD");
                    float c = ((float) strength) * 2;
                    d = a / c;
                    activateZoom();

                    try {
                        thread.sleep(10);
                    } catch (InterruptedException ignored) {}
                }

                deActivateZoom();
            }

            public void activateZoom() {
                mc.gameSettings.fovSetting = d;
                mc.gameSettings.hideGUI = hideHUD;
                mc.gameSettings.smoothCamera = smoothCam;
            }

            public void deActivateZoom() {
                mc.gameSettings.fovSetting = Zoom.a;
                mc.gameSettings.hideGUI = false;
                mc.gameSettings.smoothCamera = false;
            }
        });
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        active = false;
    }
}

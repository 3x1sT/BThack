package com.bt.BThack.impl.Module.RENDER.Zoom;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;

public class ZoomThread extends Thread implements Mc {
    public static float d;
    public static boolean smoothCam;
    public static boolean hideHUD;
    public void run() {
        while (Zoom.active) {
            float a = Zoom.a;
            double strength = Module.getSlider("Zoom", "Zoom Strength");
            smoothCam = Module.getCheckbox("Zoom", "Smooth Camera");
            hideHUD = Module.getCheckbox("Zoom", "Hide HUD");
            float c = ((float) strength) * 2;
            d = a / c;
            activateZoom();

            try {
                sleep(10);
            } catch (InterruptedException ignored) {}
        }

        deActivateZoom();
    }

    public static void activateZoom() {
        mc.gameSettings.fovSetting = d;
        mc.gameSettings.hideGUI = hideHUD;
        mc.gameSettings.smoothCamera = smoothCam;
    }

    public static void deActivateZoom() {
        mc.gameSettings.fovSetting = Zoom.a;
        mc.gameSettings.hideGUI = false;
        mc.gameSettings.smoothCamera = false;
    }
}

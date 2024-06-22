package com.bt.BThack.impl.Module.PLAYER.ChestStealer;

import com.bt.BThack.api.Module.Module;

public class StealerTimer extends Thread{
    public static int allow = 0;

    public void run() {
        long time = (long) Module.getSlider("ChestStealer", "Steal Delay");
        try {
            sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        allow = 1;
    }
}

package com.bt.BTbot.impl.AntiAFK.Doing;


import com.bt.BTbot.api.Utils.Interfaces.Mc;
import com.bt.BTbot.impl.AntiAFK.Start.StartAntiAFK;
import com.bt.BTbot.impl.AntiAFK.Start.StartAntiAFKThread;

public class Jump extends Thread implements Mc {
    public void run() {
        if (StartAntiAFKThread.startDoing) return;
        StartAntiAFKThread.startDoing = true;
        if (mc.player.onGround) {
            mc.player.jump();
        }
        double d = StartAntiAFK.delay * 1000;
        long delay = (long) d;
        try {
            sleep(delay);
        } catch (InterruptedException ignored) {}
        StartAntiAFKThread.startDoing = false;
        new StartAntiAFKThread().start();
    }
}

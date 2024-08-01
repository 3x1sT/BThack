package com.bt.BThack.impl.Module.PLAYER.Spammer;

import com.bt.BThack.api.Module.Module;

public class timerThread extends Thread{
    long a;
    public void run() {
        a = (long) (Module.getSlider("Spammer", "Delay(Second)") * 1000);
        try {
            timerThread.sleep(a);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Spammer.readTXT.Value = 0;
        new SendMessage().start();
    }
}

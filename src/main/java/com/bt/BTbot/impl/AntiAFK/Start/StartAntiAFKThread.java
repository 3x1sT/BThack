package com.bt.BTbot.impl.AntiAFK.Start;

import com.bt.BTbot.impl.AntiAFK.Doing.ActivateHand;
import com.bt.BTbot.impl.AntiAFK.Doing.GotoN.GotoN;
import com.bt.BTbot.impl.AntiAFK.Doing.GotoN.PrepareGotoN;
import com.bt.BTbot.impl.AntiAFK.Doing.Jump;
import com.bt.BTbot.impl.AntiAFK.Doing.Nothing;
import com.bt.BTbot.impl.AntiAFK.Doing.SendToChat;
import com.bt.BTbot.api.Utils.Generate.GenerateIntNumber;

public class StartAntiAFKThread extends Thread{
    public static boolean startDoing = false;

    public void run() {
        if (!StartAntiAFK.active) stop();
        if (startDoing) return;

        reset();

        int a = GenerateIntNumber.generate(0,4);
        if (a == 0) {
            new Nothing().start();
        } else if (a == 1) {
            new PrepareGotoN().start();
        } else if (a == 2) {
            new ActivateHand().start();
        } else if (a == 3) {
            new Jump().start();
        } else if (a == 4) {
            new SendToChat().start();
        }
    }

    private static void reset() {
        SendToChat.number = 0;
        PrepareGotoN.newX = 0;
        PrepareGotoN.newZ = 0;
        GotoN.needX = 0;
        GotoN.needZ = 0;
        GotoN.minX = 0;
        GotoN.minZ = 0;
        GotoN.maxX = 0;
        GotoN.maxZ = 0;
    }
}

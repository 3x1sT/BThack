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

    PrepareGotoN prepareGotoN;
    GotoN gotoN;

    public void run() {
        if (!StartAntiAFK.active) stop();
        if (startDoing) return;

        reset();

        byte a = (byte) GenerateIntNumber.generate(0,4);
        if (a == 0) {
            new Nothing().start();
        } else if (a == 1) {
            prepareGotoN = new PrepareGotoN();
            prepareGotoN.prepare();

            gotoN = new GotoN(prepareGotoN.newX, prepareGotoN.newZ, this);
            gotoN.start();
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
    }
}

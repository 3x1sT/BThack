package com.bt.BTbot.impl.AntiAFK.Start;

import com.bt.BTbot.api.Utils.Interfaces.Mc;
import com.bt.BTbot.api.Utils.SendUtils;
import com.bt.BTbot.impl.AntiAFK.Check.NullCheck;
import com.mojang.realmsclient.gui.ChatFormatting;

public class StartAntiAFK implements Mc {



    public static double radiusCenterX;
    public static double radiusCenterZ;
    public static boolean active = false;
    public static int messageSize = 5;
    public static double delay = 2.0;
    public static double walkRadius = 4;



    public static void start() {
        active = true;

        if (NullCheck.nullCheck()) return;

        radiusCenterX = mc.player.posX;
        radiusCenterZ = mc.player.posZ;

        new StartAntiAFKThread().start();

        SendUtils.sendMessage("AntiAFK " + ChatFormatting.GREEN + "enabled" + ChatFormatting.RESET + ".");
    }

    public static void stop() {
        active = false;

        if (NullCheck.nullCheck()) return;

        SendUtils.sendMessage("AntiAFK " + ChatFormatting.RED + "disabled" + ChatFormatting.RESET + ". BTBot was happy to help you :3");
    }
}

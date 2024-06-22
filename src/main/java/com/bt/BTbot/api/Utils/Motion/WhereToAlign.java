package com.bt.BTbot.api.Utils.Motion;

import com.bt.BThack.api.Utils.Interfaces.Mc;

public class WhereToAlign implements Mc {
    public static long needX = 0;
    public static long needZ = 0;


    private static float x = 0;
    private static float z = 0;
    private static long xInt;
    private static float xFloat;
    private static long zInt;
    private static float zFloat;
    private static void NumberMath() {
        x = mc.player.serverPosX;
        z = mc.player.serverPosZ;

        xInt = (long) x;
        xFloat = x - xInt;

        zInt = (long) z;
        zFloat = z - zInt;

        nearestBlock();
    }

    private static void nearestBlock() {
        if (xFloat >= 0.5) {
            needX = xInt + 1;
        } else if (xFloat < 0.5){
            needX = xInt;
        }
        if (zFloat >= 0.5) {
            needZ = zInt + 1;
        } else if (zFloat < 0.5){
            needZ = zInt;
        }
    }

    public static void execute() {
        NumberMath();
    }
}
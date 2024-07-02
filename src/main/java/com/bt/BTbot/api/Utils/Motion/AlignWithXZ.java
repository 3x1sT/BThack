package com.bt.BTbot.api.Utils.Motion;

import com.bt.BTbot.api.Utils.Rotate.RotateMath;
import com.bt.BThack.api.Utils.Interfaces.Mc;

public class AlignWithXZ implements Mc {
    private static double minX;
    private static double maxX;
    private static double minZ;
    private static double maxZ;
    public static void alignWithXZ() {
        WhereToAlign.execute();

        minX = WhereToAlign.needX - 0.03;
        maxX = WhereToAlign.needX + 0.03;

        minZ = WhereToAlign.needZ - 0.03;
        maxZ = WhereToAlign.needZ + 0.03;

        while (!check()) {
            rotate();
            mc.gameSettings.keyBindForward.pressed = true;
        }
        mc.gameSettings.keyBindForward.pressed = false;
    }

    private static void rotate() {
        mc.player.rotationYaw = RotateMath.rotations(WhereToAlign.needX, mc.player.posY, WhereToAlign.needZ)[0];
    }

    private static boolean check() {
        return (mc.player.posX > minX && mc.player.posX < maxX) && (mc.player.posZ > minZ && mc.player.posZ < maxZ);
    }
}

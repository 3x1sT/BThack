package com.bt.BTbot.api.Utils.Motion;

import com.bt.BTbot.api.Utils.Rotate.RotateMath;
import com.bt.BThack.api.Utils.Interfaces.Mc;

class AlignThread extends Thread implements Mc {
    boolean isMoving = false;

    private final double needX;
    private final double needZ;
    private final double minX;
    private final double maxX;
    private final double minZ;
    private final double maxZ;

    public AlignThread(double needX, double needZ, double minX, double maxX, double minZ, double maxZ) {
        this.needX = needX;
        this.needZ = needZ;
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }


    @Override
    public void run() {
        isMoving = true;
        float yaw = mc.player.rotationYaw;

        while (!check()) {
            rotate();
            mc.gameSettings.keyBindForward.pressed = true;

            try {
                sleep(10);
            } catch (InterruptedException ignored) {}
        }
        mc.gameSettings.keyBindForward.pressed = false;
        mc.player.rotationYaw = yaw;
        isMoving = false;
    }



    private void rotate() {
        mc.player.rotationYaw = RotateMath.rotations(needX, mc.player.posY, needZ)[0];
    }

    private boolean check() {
        return (mc.player.posX > minX && mc.player.posX < maxX) && (mc.player.posZ > minZ && mc.player.posZ < maxZ);
    }
}

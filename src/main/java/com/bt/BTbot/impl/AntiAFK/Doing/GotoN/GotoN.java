package com.bt.BTbot.impl.AntiAFK.Doing.GotoN;

import com.bt.BTbot.api.Utils.Interfaces.Mc;
import com.bt.BTbot.api.Utils.Rotate.RotateMath;
import com.bt.BTbot.impl.AntiAFK.Doing.Nothing;
import com.bt.BTbot.impl.AntiAFK.Start.StartAntiAFKThread;

public class GotoN extends Thread implements Mc {
    public double needX;
    public double needZ;
    public double maxX;
    public double minX;
    public double maxZ;
    public double minZ;
    protected boolean pause;
    protected boolean cancel;

    StartAntiAFKThread startAntiAFKThread;

    public GotoN(double needX, double needZ, StartAntiAFKThread thread) {
        this.needX = needX;
        this.needZ = needZ;

        this.startAntiAFKThread = thread;
    }

    public void run() {

        while (mc.player.posX != needX && mc.player.posZ != needZ) {
            maxX = needX + 0.15;
            minX = needX - 0.15;
            maxZ = needZ + 0.15;
            minZ = needZ - 0.15;

            mc.player.rotationYaw = RotateMath.rotations(needX, mc.player.posY, needZ)[0];
            mc.gameSettings.keyBindForward.pressed = true;

            if (mc.player.collidedHorizontally) {
                pause = true;
                tryJump();
            }

            if (!toFarX() && !toFarZ()) {
                mc.gameSettings.keyBindForward.pressed = false;
                if (startAntiAFKThread != null)
                    StartAntiAFKThread.startDoing = false;
                new Nothing().start();
                cancel = false;
                pause = false;
                stop();
            }

            if (cancel) {
                if (startAntiAFKThread != null) {
                    StartAntiAFKThread.startDoing = false;
                    new Nothing().start();
                }
                cancel = false;
                pause = false;
                stop();
            }
        }
        if (startAntiAFKThread != null) {
            StartAntiAFKThread.startDoing = false;
            new Nothing().start();
        }
        cancel = false;
        pause = false;
        stop();
    }

    public void tryJump() {
        mc.gameSettings.keyBindForward.pressed = false;
        double oldPosY = mc.player.posY;
        mc.player.jump();
        mc.gameSettings.keyBindForward.pressed = true;
        try {
            sleep(600);
        } catch (InterruptedException ignored) {}
        mc.gameSettings.keyBindForward.pressed = false;
        if (mc.player.posY < oldPosY + 0.4) {
            mc.player.jump();
            mc.gameSettings.keyBindForward.pressed = true;
            try {
                sleep(600);
            } catch (InterruptedException ignored) {}
            mc.gameSettings.keyBindForward.pressed = false;
        } else {
            pause = false;
        }
        if (mc.player.posY < oldPosY + 0.4) {
            mc.player.jump();
            mc.gameSettings.keyBindForward.pressed = true;
            try {
                sleep(600);
            } catch (InterruptedException ignored) {}
            mc.gameSettings.keyBindForward.pressed = false;
        } else {
            pause = false;
        }
        if (mc.player.posY < oldPosY + 0.4) {
            cancel = true;
            pause = false;
        }
    }

    private boolean toFarX() {
        return mc.player.posX > maxX || mc.player.posX < minX;
    }

    private boolean toFarZ() {
        return mc.player.posZ > maxZ || mc.player.posZ < minZ;
    }
}

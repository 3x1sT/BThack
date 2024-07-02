package com.bt.BTbot.impl.AntiAFK.Doing.GotoN;

import com.bt.BTbot.api.Utils.Interfaces.Mc;
import com.bt.BTbot.impl.AntiAFK.Doing.Nothing;
import com.bt.BTbot.impl.AntiAFK.Start.StartAntiAFK;
import com.bt.BTbot.impl.AntiAFK.Start.StartAntiAFKThread;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class GotoN extends Thread implements Mc {
    public static double needX;
    public static double needZ;
    public static double maxX;
    public static double minX;
    public static double maxZ;
    public static double minZ;
    protected static boolean pause;
    protected static boolean cancel;
    public void run() {
        if (!PrepareGotoN.negative) {
            needX = StartAntiAFK.radiusCenterX + PrepareGotoN.newX;
            needZ = StartAntiAFK.radiusCenterZ + PrepareGotoN.newZ;
        } else {
            needX = StartAntiAFK.radiusCenterX - PrepareGotoN.newX;
            needZ = StartAntiAFK.radiusCenterZ - PrepareGotoN.newZ;
        }

        while (mc.player.posX != needX && mc.player.posZ != needZ) {
            maxX = needX + 0.15;
            minX = needX - 0.15;
            maxZ = needZ + 0.15;
            minZ = needZ - 0.15;

            mc.player.rotationYaw = rotations()[0];
            mc.gameSettings.keyBindForward.pressed = true;

            if (mc.player.collidedHorizontally) {
                pause = true;
                tryJump();
            }

            if (!toFarX() && !toFarZ()) {
                mc.gameSettings.keyBindForward.pressed = false;
                StartAntiAFKThread.startDoing = false;
                new Nothing().start();
                cancel = false;
                pause = false;
                stop();
            }

            if (cancel) {
                StartAntiAFKThread.startDoing = false;
                new Nothing().start();
                cancel = false;
                pause = false;
                stop();
            }
        }
        StartAntiAFKThread.startDoing = false;
        new Nothing().start();
        cancel = false;
        pause = false;
        stop();
    }

    public static float[] rotations() {
        Minecraft mc = Minecraft.getMinecraft();
        double x = needX - mc.player.posX;
        double y = mc.player.posY - (mc.player.posY + mc.player.getEyeHeight() - 0.7);
        double z = needZ - mc.player.posZ;

        double u = MathHelper.sqrt(x * x + z *z);

        float u2 = (float) (MathHelper.atan2(z, x) * (180D / Math.PI) - 90.0F);
        float u3 = (float) (-MathHelper.atan2(y, u) * (180D / Math.PI));

        return new float[]{u2, u3};
    }

    public static void tryJump() {
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

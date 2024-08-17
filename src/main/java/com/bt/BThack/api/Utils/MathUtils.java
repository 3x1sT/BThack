package com.bt.BThack.api.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public final class MathUtils {

    public static double[] directionSpeed(double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0F) {
            if (side > 0.0F) {
                yaw += (float)(forward > 0.0F ? -45 : 45);
            } else if (side < 0.0F) {
                yaw += (float)(forward > 0.0F ? 45 : -45);
            }

            side = 0.0F;
            if (forward > 0.0F) {
                forward = 1.0F;
            } else if (forward < 0.0F) {
                forward = -1.0F;
            }
        }

        double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
        double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
        double posX = (double)forward * speed * cos + (double)side * speed * sin;
        double posZ = (double)forward * speed * sin - (double)side * speed * cos;
        return new double[]{posX, posZ};
    }

    public static double map(double value, double a, double b, double c, double d) {
        value = (value - a) / (b - a);
        return c + value * (d - c);
    }

    public static double getDistance(Vec3d pos, double x, double y, double z) {
        double deltaX = pos.x - x;
        double deltaY = pos.y - y;
        double deltaZ = pos.z - z;
        return MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    public static double roundNumber(double number, int scale) {
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }


    public static int mirrorNumber(int minValue, int value, int maxValue) {
        if (value == maxValue)
            return minValue;
        if (value == minValue)
            return maxValue;
        if (value > maxValue) {
            return minValue - (value - maxValue);
        }
        if (value < minValue) {
            return maxValue + (minValue - value);
        }

        minValue--;
        maxValue++;

        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Integer> revertedNumbers = new ArrayList<>();
        for (int i = minValue; i < maxValue + 1; i++) {
            numbers.add(i);
        }
        for (int i = numbers.size() - 1; i > 0; i--) {
            revertedNumbers.add(numbers.get(i));
        }
        return revertedNumbers.get(value);
    }

    public static int removeNumbers(int number, int numbers) {
        int temp = number;
        while (temp - numbers > 0) {
            temp -= numbers;
        }
        return temp;
    }

    public static double getDistance(Vec3d from, Vec3d to) {
        float f = (float)(from.x - to.x);
        float g = (float)(from.y - to.y);
        float h = (float)(from.z - to.z);
        return MathHelper.sqrt(f * f + g * g + h * h);
    }
}

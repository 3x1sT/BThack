package com.bt.BTbot.api.Utils.Rotate;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class RotateMath {
    public static float[] rotations(double needX, double needY, double needZ) {
        Minecraft mc = Minecraft.getMinecraft();
        double x = needX - mc.player.posX;
        double y = needY - (mc.player.posY + mc.player.getEyeHeight() - 0.7);
        double z = needZ - mc.player.posZ;

        double u = MathHelper.sqrt(x * x + z * z);

        float u2 = (float) (MathHelper.atan2(z, x) * (180D / Math.PI) - 90.0F);
        float u3 = (float) (-MathHelper.atan2(y, u) * (180D / Math.PI));

        return new float[]{u2, u3};
    }
}

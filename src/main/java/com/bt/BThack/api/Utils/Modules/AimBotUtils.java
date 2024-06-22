package com.bt.BThack.api.Utils.Modules;

import com.bt.BThack.api.Utils.Entity.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class AimBotUtils {
    public static void RotateToEntity(Entity target) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.player.rotationYaw = rotations(target)[0];
        mc.player.rotationPitch = rotations(target)[1];
    }

    public static void packetRotateToEntity(Entity target) {
        float yaw = rotations(target)[0];
        float pitch = rotations(target)[1];

        PlayerUtil.PacketFacePitchAndYaw(pitch, yaw);
    }



    public static float[] rotations(Entity entity) {
        Minecraft mc = Minecraft.getMinecraft();
        double x = entity.posX - mc.player.posX;
        double y = entity.posY - (mc.player.posY + mc.player.getEyeHeight() - 0.7);
        double z = entity.posZ - mc.player.posZ;

        double u = MathHelper.sqrt(x * x + z * z);

        float u2 = (float) (MathHelper.atan2(z, x) * (180D / Math.PI) - 90.0F);
        float u3 = (float) (-MathHelper.atan2(y, u) * (180D / Math.PI));

        return new float[]{u2, u3};
    }

    public static String getDirection(EntityPlayer entity) {
        String look = "ERROR!";

        switch (NoRotateMathUtil.RotateYawMath(entity)) {
            case 45:
            case -315:
                look = "X- Z+";
                break;
            case 90:
            case -270:
                look = "X-";
                break;
            case 135:
            case -225:
                look = "X- Z-";
                break;
            case 180:
            case -180:
                look = "Z-";
                break;
            case 225:
            case -135:
                look = "X+ Z-";
                break;
            case 270:
            case -90:
                look = "X+";
                break;
            case 315:
            case -45:
                look = "X+ Z+";
                break;
            case 0:
            case 360:
            case -360:
                look = "Z+";
                break;
        }

        return look;
    }
}

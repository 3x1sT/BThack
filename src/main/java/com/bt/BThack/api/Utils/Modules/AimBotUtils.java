package com.bt.BThack.api.Utils.Modules;

import com.bt.BThack.api.Utils.Entity.PlayerUtil;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AimBotUtils implements Mc {
    public static void RotateToEntity(Entity target) {
        mc.player.rotationYaw = rotations(target)[0];
        mc.player.rotationPitch = rotations(target)[1];
    }

    public static void packetRotateToEntity(Entity target) {
        float yaw = rotations(target)[0];
        float pitch = rotations(target)[1];

        PlayerUtil.packetFacePitchAndYaw(pitch, yaw);
    }



    public static float[] rotations(Entity entity) {
        double x = entity.posX - mc.player.posX;
        double y = entity.posY - (mc.player.posY + mc.player.getEyeHeight() - 0.7);
        double z = entity.posZ - mc.player.posZ;

        double u = MathHelper.sqrt(x * x + z * z);

        float u2 = (float) (MathHelper.atan2(z, x) * (180D / Math.PI) - 90.0F);
        float u3 = (float) (-MathHelper.atan2(y, u) * (180D / Math.PI));

        return new float[]{u2, u3};
    }

    public static float[] rotations(BlockPos pos) {
        double x = pos.getX() - mc.player.posX;
        double y = pos.getY() - (mc.player.posY + mc.player.getEyeHeight() - 0.7);
        double z = pos.getZ() - mc.player.posZ;

        double u = MathHelper.sqrt(x * x + z * z);

        float u2 = (float) (MathHelper.atan2(z, x) * (180D / Math.PI) - 90.0F);
        float u3 = (float) (-MathHelper.atan2(y, u) * (180D / Math.PI));

        return new float[]{u2, u3};
    }


    public static byte[] getCordFactorFromDirection(Entity entity) {
        switch (getAbsDirection(entity)) {
            case 45:
                return new byte[]{-1, 1};
            case 90:
                return new byte[]{-1, 0};
            case 135:
                return new byte[]{-1,-1};
            case 180:
                return new byte[]{0, -1};
            case 225:
                return new byte[]{1, -1};
            case 270:
                return new byte[]{1, 0};
            case 315:
                return new byte[]{1, 1};
            case 0:
                return new byte[]{0, 1};
            default:
                return new byte[]{0,0};
        }
    }

    public static String getDirection(Entity entity) {
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

    public static String getDirection(float yaw) {
        String look = "ERROR!";

        switch (NoRotateMathUtil.RotateYawMath(yaw)) {
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

    public static short getAbsDirection(Entity entity) {
        switch (NoRotateMathUtil.RotateYawMath(entity)) {
            case 45:
            case -315:
                return 45;
            case 90:
            case -270:
                return 90;
            case 135:
            case -225:
                return 135;
            case 180:
            case -180:
                return 180;
            case 225:
            case -135:
                return 225;
            case 270:
            case -90:
                return 270;
            case 315:
            case -45:
                return 315;
            case 0:
            case 360:
            case -360:
            default:
                return 0;
        }
    }
    
    public static int getRoundedToCornersEntityRotation(Entity entity) {
        int yaw;

        switch (AimBotUtils.getDirection(entity)) {
            case "X- Z+":
            case  "X-":
                yaw = -45;
                break;
            case "X- Z-":
            case "Z-":
                yaw = 45;
                break;
            case "X+ Z-":
            case "X+":
                yaw = 135;
                break;
            case "X+ Z+":
            case "Z+":
            default:    
                yaw = -135;
                break;
        }
        
        return yaw;
    }

    public static String getRoundedToCornersEntityRotationOnString(Entity entity) {
        switch (AimBotUtils.getDirection(entity)) {
            case "X- Z+":
            case  "X-":
                return "X-";
            case "X- Z-":
            case "Z-":
                return "Z-";
            case "X+ Z-":
            case "X+":
                return "X+";
            case "X+ Z+":
            case "Z+":
            default:
                return "Z+";
        }
    }

    public static String getRoundedToCornersEntityRotationOnString(float yaw) {
        switch (AimBotUtils.getDirection(yaw)) {
            case "X- Z+":
            case  "X-":
                return "X-";
            case "X- Z-":
            case "Z-":
                return "Z-";
            case "X+ Z-":
            case "X+":
                return "X+";
            case "X+ Z+":
            case "Z+":
            default:
                return "Z+";
        }
    }

    public static int getRoundedToStraightEntityRotation(Entity entity) {
        int yaw;

        switch (AimBotUtils.getDirection(entity)) {
            case "X- Z+":
            case  "X-":
                yaw = 90;
                break;
            case "X- Z-":
            case "Z-":
                yaw = 180;
                break;
            case "X+ Z-":
            case "X+":
                yaw = 270;
                break;
            case "X+ Z+":
            case "Z+":
            default:
                yaw = 0;
                break;
        }

        return yaw;
    }

    public static EnumFacing getFacingEntity(Entity entity) {
        float pitch = mc.player.rotationPitch;
        if (-45 > pitch) {
            return EnumFacing.UP;
        }
        if (45 < pitch) {
            return EnumFacing.DOWN;
        }

        switch (AimBotUtils.getDirection(entity)) {
            case "X- Z+":
            case  "X-":
                return EnumFacing.WEST;
            case "X- Z-":
            case "Z-":
                return EnumFacing.NORTH;
            case "X+ Z-":
            case "X+":
                return EnumFacing.EAST;
            case "X+ Z+":
            case "Z+":
            default:
                return EnumFacing.SOUTH;
        }
    }

    public static EnumFacing getInvertedFacingEntity(Entity entity) {
        float pitch = mc.player.rotationPitch;
        if (-45 > pitch) {
            return EnumFacing.DOWN;
        }
        if (45 < pitch) {
            return EnumFacing.UP;
        }

        switch (AimBotUtils.getDirection(entity)) {
            case "X- Z+":
            case  "X-":
                return EnumFacing.EAST;
            case "X- Z-":
            case "Z-":
                return EnumFacing.SOUTH;
            case "X+ Z-":
            case "X+":
                return EnumFacing.WEST;
            case "X+ Z+":
            case "Z+":
            default:
                return EnumFacing.NORTH;
        }
    }
}

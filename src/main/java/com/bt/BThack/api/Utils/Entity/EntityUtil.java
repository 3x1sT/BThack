package com.bt.BThack.api.Utils.Entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EntityUtil
{
    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me)
    {
        double dirX = me.posX - px;
        double dirY = me.posY - py;
        double dirZ = me.posZ - pz;

        double len = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);

        dirX /= len;
        dirY /= len;
        dirZ /= len;

        double pitch = Math.asin(dirY);
        double yaw = Math.atan2(dirZ, dirX);

        // to degree
        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]
                { yaw, pitch };
    }

    public static double GetDistance(double p_X, double p_Y, double p_Z, double x, double y, double z)
    {
        double d0 = p_X - x;
        double d1 = p_Y - y;
        double d2 = p_Z - z;
        return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public static double GetDistanceOfEntityToBlock(Entity p_Entity, BlockPos p_Pos)
    {
        return GetDistance(p_Entity.posX, p_Entity.posY, p_Entity.posZ, p_Pos.getX(), p_Pos.getY(), p_Pos.getZ());
    }
}

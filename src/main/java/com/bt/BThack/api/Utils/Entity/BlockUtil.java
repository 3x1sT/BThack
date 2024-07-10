package com.bt.BThack.api.Utils.Entity;

import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BlockUtil implements Mc {


    public static void placeBlock(BlockPos pos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {

            if (!mc.world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !isIntercepted(pos)) {

                Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D);

                float[] old = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};

                mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float) Math.toDegrees(Math.atan2((vec.z - mc.player.posZ), (vec.x - mc.player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec.y - (mc.player.posY + (double) mc.player.getEyeHeight())), (Math.sqrt((vec.x - mc.player.posX) * (vec.x - mc.player.posX) + (vec.z - mc.player.posZ) * (vec.z - mc.player.posZ)))))), mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(old[0], old[1], mc.player.onGround));

                return;
            }
        }
    }

    public static boolean isIntercepted(BlockPos pos) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCanBeSupplied(BlockPos pos) {
        if (BuildManager.ignoreBlocks.contains(mc.world.getBlockState(new BlockPos(pos.x + 1, pos.y, pos.z)).getBlock())) {
            if (BuildManager.ignoreBlocks.contains(mc.world.getBlockState(new BlockPos(pos.x - 1, pos.y, pos.z)).getBlock())) {
                if (BuildManager.ignoreBlocks.contains(mc.world.getBlockState(new BlockPos(pos.x, pos.y + 1, pos.z)).getBlock())) {
                    if (BuildManager.ignoreBlocks.contains(mc.world.getBlockState(new BlockPos(pos.x, pos.y - 1, pos.z)).getBlock())) {
                        if (BuildManager.ignoreBlocks.contains(mc.world.getBlockState(new BlockPos(pos.x, pos.y, pos.z + 1)).getBlock())) {
                            return !BuildManager.ignoreBlocks.contains(mc.world.getBlockState(new BlockPos(pos.x, pos.y, pos.z - 1)).getBlock());
                        }
                    }
                }
            }
        }
        return true;
    }
}

package com.bt.BThack.api.Utils.Build;

import com.bt.BThack.api.Managers.RenderManager.BoxColor;
import com.bt.BThack.api.Managers.RenderManager.RenderManager;
import com.bt.BThack.api.Utils.BlockUtil;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Set;

public class BuildManager implements Mc {

    public static volatile boolean isBuilding = false;


    protected static void build(BlockPos pos, Thread thread) {
        for (EnumFacing enumFacing : EnumFacing.values()) {

            if (!mc.world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !BlockUtil.isIntercepted(pos)) {

                Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D);

                float[] old = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};


                mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float) Math.toDegrees(Math.atan2((vec.z - mc.player.posZ), (vec.x - mc.player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec.y - (mc.player.posY + (double) mc.player.getEyeHeight())), (Math.sqrt((vec.x - mc.player.posX) * (vec.x - mc.player.posX) + (vec.z - mc.player.posZ) * (vec.z - mc.player.posZ)))))), mc.player.onGround));

                try {
                    thread.sleep(5);
                } catch (InterruptedException ignored) {}

                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.MAIN_HAND);

                try {
                    thread.sleep(5);
                } catch (InterruptedException ignored) {}

                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

                try {
                    thread.sleep(5);
                } catch (InterruptedException ignored) {}

                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(old[0], old[1], mc.player.onGround));

                return;
            }
        }
    }

    public static void buildWithoutChecks(BlockPos pos, Thread thread) {
        for (EnumFacing enumFacing : EnumFacing.values()) {

            if (!mc.world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR)) {

                Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D);

                float[] old = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};


                mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float) Math.toDegrees(Math.atan2((vec.z - mc.player.posZ), (vec.x - mc.player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec.y - (mc.player.posY + (double) mc.player.getEyeHeight())), (Math.sqrt((vec.x - mc.player.posX) * (vec.x - mc.player.posX) + (vec.z - mc.player.posZ) * (vec.z - mc.player.posZ)))))), mc.player.onGround));

                try {
                    thread.sleep(5);
                } catch (InterruptedException ignored) {}

                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.MAIN_HAND);

                try {
                    thread.sleep(5);
                } catch (InterruptedException ignored) {}

                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

                try {
                    thread.sleep(5);
                } catch (InterruptedException ignored) {}

                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(old[0], old[1], mc.player.onGround));

                return;
            }
        }
    }

    protected static boolean check(ArrayList<BlockPos> positions) {
        for (BlockPos pos : positions) {
            if (ignoreBlocks.contains(mc.world.getBlockState(pos).getBlock())) {
                return true;
            }
        }
        return false;
    }



    public static final Set<Block> ignoreBlocks = Sets.newHashSet(
            Blocks.AIR,
            Blocks.WATER,
            Blocks.FLOWING_WATER,
            Blocks.LAVA,
            Blocks.FLOWING_LAVA
    );

    public static void delay(long milliseconds, BlockPos pos, Thread thread) {
        for (long i = 0; i < milliseconds; i += 10) {
            RenderManager.addRenderBox(pos, new BoxColor(0, 1, 0, 1, 0, 1, 0, 0.5f));

            try {
                thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
    }

    public static boolean isPossibleRich(BlockPos blockPos) {
        return mc.player.getDistance(blockPos.x, blockPos.y, blockPos.z) < 7.5;
    }
}

package com.bt.BThack.impl.Module.MOVEMENT.ElytraFlight;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.*;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.RayTraceResult.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorldExtensions implements Mc {

    public static boolean isLiquidBelow(World world, Entity entity) {
        List<RayTraceResult> results = rayTraceBoundingBoxToGround(world, entity, true);
        if (results.stream().allMatch(it -> it.typeOfHit == Type.MISS || (it.hitVec != null ? it.hitVec.y : 911.0) < 0.0)) {
            return true;
        }

        Optional<BlockPos> pos = results.stream()
                .max((a, b) -> Double.compare(a.hitVec != null ? a.hitVec.y : -69420.0, b.hitVec != null ? b.hitVec.y : -69420.0))
                .map(RayTraceResult::getBlockPos);

        return pos.isPresent() && isLiquid(world, pos.get());
    }

    private static List<RayTraceResult> rayTraceBoundingBoxToGround(World world, Entity entity, boolean stopOnLiquid) {
        AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
        double[] xArray = {Math.floor(boundingBox.minX), Math.floor(boundingBox.maxX)};
        double[] zArray = {Math.floor(boundingBox.minZ), Math.floor(boundingBox.maxZ)};

        List<RayTraceResult> results = new ArrayList<>(4);

        for (double x : xArray) {
            for (double z : zArray) {
                RayTraceResult result = rayTraceToGround(world, new Vec3d(x, boundingBox.minY, z), stopOnLiquid);
                if (result != null) {
                    results.add(result);
                }
            }
        }

        return results;
    }

    public static Vec3d getGroundPos(World world, Entity entity) {
        List<RayTraceResult> results = rayTraceBoundingBoxToGround(world, entity, false);
        if (results.stream().allMatch(it -> it.typeOfHit == Type.MISS || (it.hitVec != null ? it.hitVec.y : 911.0) < 0.0)) {
            return new Vec3d(0.0, -999.0, 0.0);
        }

        return results.stream()
                .max((a, b) -> Double.compare(a.hitVec != null ? a.hitVec.y : -69420.0, b.hitVec != null ? b.hitVec.y : -69420.0))
                .map(it -> it.hitVec)
                .orElse(new Vec3d(0.0, -69420.0, 0.0));
    }

    private static RayTraceResult rayTraceToGround(World world, Vec3d vec3d, boolean stopOnLiquid) {
        return world.rayTraceBlocks(vec3d, new Vec3d(vec3d.x, -1.0, vec3d.z), stopOnLiquid, true, false);
    }

    public static boolean isVisible(World world, BlockPos pos, double tolerance) {
        EntityPlayerSP player = mc.player;
        if (player == null) return false;

        Vec3d center = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        RayTraceResult result = world.rayTraceBlocks(
                player.getPositionEyes(1.0f),
                center,
                false,
                true,
                false
        );

        return result != null && (result.getBlockPos().equals(pos) ||
                (result.hitVec != null && result.hitVec.distanceTo(center) <= tolerance));
    }

    public static RayTraceResult rayTrace(World world, Vec3d start, Vec3d end, boolean stopOnLiquid,
                                          boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        return world.rayTraceBlocks(start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
    }

    public static boolean isLiquid(World world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isLiquid();
    }

    public static boolean isWater(World world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isLiquid() && world.getBlockState(pos).getBlock() instanceof BlockLiquid;
    }

    public static boolean hasNeighbour(World world, BlockPos pos) {
        for (EnumFacing facing : EnumFacing.values()) {
            if (!world.getBlockState(pos.offset(facing)).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPlaceable(World world, BlockPos pos, boolean ignoreSelfCollide) {
        return world.getBlockState(pos).getMaterial().isReplaceable() &&
                world.checkNoEntityCollision(new AxisAlignedBB(pos), ignoreSelfCollide ? mc.player : null) &&
                world.getWorldBorder().contains(pos) &&
                !isOutsideBuildHeight(pos);
    }

    private static boolean isOutsideBuildHeight(BlockPos pos) {
        return pos.getY() < 0 || pos.getY() >= 256;
    }
}
package com.bt.BThack.api.Utils;

import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil implements Mc {

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

    public static List<BlockPos> getNearbyBlocks(EntityPlayer entityPlayer, double blockRange, boolean motion) {
        List<BlockPos> nearbyBlocks = new ArrayList<>();

        int range = (int) MathUtils.roundNumber(blockRange, 0);

        if (motion) {
            entityPlayer.getPosition().add(new Vec3i(entityPlayer.motionX, entityPlayer.motionY, entityPlayer.motionZ));
        }

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    nearbyBlocks.add(entityPlayer.getPosition().add(x, y, z));
                }
            }
        }

        return nearbyBlocks;
    }

    public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleBlocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        circleBlocks.add(new BlockPos(x, y + plus_y, z));
                    }
                }
            }
        }
        return circleBlocks;
    }

    public static Item getItemFromName(String itemName) {
        ResourceLocation resourcelocation = new ResourceLocation(itemName);
        Item item = Item.REGISTRY.getObject(resourcelocation);
        return item;
    }
}

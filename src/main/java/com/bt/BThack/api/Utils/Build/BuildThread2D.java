package com.bt.BThack.api.Utils.Build;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;

// TODO: Still need to test it as there are many flaws.
public class BuildThread2D extends Thread implements Mc {
    private int delayTicks;
    private ArrayList<Vec2f> buildSchematic2D;

    private BlockPos startPos;
    private int direction;

    public void set2DSchematic(int delayTicks, ArrayList<Vec2f> buildSchematic2D, BlockPos startPos, int direction) {
        this.delayTicks = delayTicks;
        this.buildSchematic2D = buildSchematic2D;
        this.startPos = startPos;
        this.direction = direction;
    }

    @Override
    public void run() {
        if (mc.player == null || mc.world == null) return;

        BuildManager.isBuilding = true;

        ArrayList<BlockPos> positions = new ArrayList<>();

        for (Vec2f vector : buildSchematic2D) {
            switch (direction) {
                case 90:
                    positions.add(new BlockPos(new Vec3i(startPos.x, startPos.y + vector.y, startPos.z - vector.x)));
                    break;
                case 180:
                    positions.add(new BlockPos(new Vec3i(startPos.x + vector.x, startPos.y + vector.y, startPos.z)));
                    break;
                case 270:
                    positions.add(new BlockPos(new Vec3i(startPos.x, startPos.y + vector.y, startPos.z + vector.x)));
                    break;
                case 0:
                default:
                    positions.add(new BlockPos(new Vec3i(startPos.x - vector.x, startPos.y + vector.y, startPos.z)));
            }
        }

        while (BuildManager.check(positions)) {
            for (BlockPos pos : positions) {
                Block block = mc.world.getBlockState(pos).getBlock();

                if (BuildManager.ignoreBlocks.contains(block)) {

                    BuildManager.build(pos, this);
                    BuildManager.delay((long) (delayTicks * mc.timer.tickLength) - 15, pos, this);
                }
            }
        }

        BuildManager.isBuilding = false;
    }
}

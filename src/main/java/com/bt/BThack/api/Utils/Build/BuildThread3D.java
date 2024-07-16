package com.bt.BThack.api.Utils.Build;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;

public class BuildThread3D extends Thread implements Mc {

    private int delayTicks;
    private ArrayList<Vec3d> buildSchematic3D;

    private BlockPos startPos;


    public void set3DSchematic(int delayTicks, ArrayList<Vec3d> buildSchematic3D, BlockPos startPos) {
        this.delayTicks = delayTicks;
        this.buildSchematic3D = buildSchematic3D;
        this.startPos = startPos;
    }

    @Override
    public void run() {

        if (mc.player == null || mc.world == null) return;

        BuildManager.isBuilding = true;

        ArrayList<BlockPos> positions = new ArrayList<>();

        for (Vec3d vector : buildSchematic3D) {
            positions.add(new BlockPos(new Vec3i(startPos.x + vector.x, startPos.y + vector.y, startPos.z + vector.z)));
        }

        while (BuildManager.check(positions)) {
            for (BlockPos pos : positions) {
                Block block = mc.world.getBlockState(pos).getBlock();

                if (BuildManager.ignoreBlocks.contains(block)) {

                    BuildManager.build(pos, this);
                    BuildManager.delay((long) (delayTicks * mc.timer.tickLength), pos, this);
                }
            }
        }

        BuildManager.isBuilding = false;
    }
}

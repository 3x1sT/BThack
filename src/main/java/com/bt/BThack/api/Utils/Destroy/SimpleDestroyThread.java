package com.bt.BThack.api.Utils.Destroy;

import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.bt.BThack.impl.Module.PLAYER.AutoTool;
import net.minecraft.util.math.BlockPos;

public class SimpleDestroyThread extends Thread implements Mc {
    BlockPos pos;

    public SimpleDestroyThread(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void run() {
        if (mc.player == null || mc.world == null) {
            DestroyManager.isDestroying = false;
            return;
        }

        DestroyManager.isDestroying = true;

        float oldYaw = mc.player.rotationYaw;
        float oldPitch = mc.player.rotationPitch;


        while (!BuildManager.ignoreBlocks.contains(mc.world.getBlockState(pos).getBlock()) || !AutoTool.ignoreBlocks.contains(mc.world.getBlockState(pos).getBlock())) {
            mc.player.rotationYaw = AimBotUtils.rotations(pos)[0];
            mc.player.rotationPitch = AimBotUtils.rotations(pos)[1];
            AutoTool.equipBestSlot(mc.world.getBlockState(pos));

            DestroyManager.currentBlockPos = pos;

            DestroyManager.delay(50, this);
        }



        mc.player.rotationYaw = oldYaw;
        mc.player.rotationPitch = oldPitch;

        DestroyManager.currentBlockPos = null;

        DestroyManager.isDestroying = false;
    }
}

package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Jesus extends Module {

    public Jesus() {
        super("Jesus",
                "Allows the player to walk on water.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("Solid");
        options.add("Dolphin");

        addMode("Mode", this, options);
        addSlider("Micro Jump Factor", this, 0.1, 0.005, 0.5, false);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        String Mode = getMode(this.name, "Mode");
        double jumpFactor = getSlider(this.name, "Micro Jump Factor");

        arrayListInfo = Mode;

        switch (Mode) {
            case "Dolphin":
                if (mc.player.isInWater() || mc.player.isInLava()) {
                    if (!mc.player.collidedHorizontally || !mc.player.isSneaking()) {
                        mc.player.motionY = jumpFactor;
                        mc.player.onGround = true;
                    }
                }
                break;
            case "Solid":

                BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.05, mc.player.posZ);
                Block block = mc.world.getBlockState(blockPos).getBlock();

                BlockPos oldPos = new BlockPos(mc.player.prevPosX, mc.player.prevPosY - 0.05, mc.player.prevPosZ);
                Block oldBlock = mc.world.getBlockState(oldPos).getBlock();

                BlockPos upPos = new BlockPos(mc.player.posX, mc.player.posY + 0.15, mc.player.posZ);
                Block upBlock = mc.world.getBlockState(upPos).getBlock();

                if (block == Blocks.WATER || block == Blocks.LAVA) {
                    if (upBlock == Blocks.WATER || upBlock == Blocks.LAVA) {
                        mc.player.motionY = 0.20;
                    } else {
                        if (oldBlock != Blocks.WATER && oldBlock != Blocks.LAVA && oldBlock != Blocks.AIR) {
                            mc.player.motionY = jumpFactor;
                        } else {
                            mc.player.motionY = 0;
                            mc.player.collidedVertically = true;
                            mc.player.onGround = true;
                        }
                    }
                }
                break;
        }
    }
}
package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.Build.BuildThread3D;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.BlockUtil;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;

public class Scaffold extends Module {

    ArrayList<Vec3d> buildVector = new ArrayList<>(Collections.singletonList(new Vec3d(0, 0, 0)));

    public Scaffold() {
        super("Scaffold",
                "Automatically puts blocks underneath you.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );
    }


    @SubscribeEvent
    public void onCLientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.2, mc.player.posZ);

        if (BuildManager.ignoreBlocks.contains(mc.world.getBlockState(blockPos).getBlock())) {
            boolean isBlocksFinded = false;

            if (!(mc.player.itemStackMainHand.getItem() instanceof ItemBlock)) {
                for (int needSlot = 0; needSlot < 36; needSlot++) {
                    Item item = mc.player.inventory.getStackInSlot(needSlot).getItem();
                    if (item instanceof ItemBlock) {

                        if (needSlot < 9) {
                            mc.player.inventory.currentItem = needSlot;

                        } else {
                            mc.playerController.updateController();
                            mc.playerController.windowClick(0, needSlot, mc.player.inventory.currentItem, ClickType.SWAP, mc.player);

                        }
                        mc.playerController.updateController();
                        isBlocksFinded = true;
                        break;
                    }
                }
            } else
                isBlocksFinded = true;

            if (!isBlocksFinded) {
                sendError("No matching blocks were found in the inventory.");
                toggle();
                return;
            }

            if (!BlockUtil.isIntercepted(blockPos)) {
                if (!BlockUtil.isCanBeSupplied(blockPos)) {
                    BlockPos blockPosTemp;
                    blockPosTemp = new BlockPos(blockPos.x + 1, blockPos.y, blockPos.z);
                    if (!BlockUtil.isCanBeSupplied(blockPosTemp)) {
                        blockPosTemp = new BlockPos(blockPos.x - 1, blockPos.y, blockPos.z);
                        if (!BlockUtil.isCanBeSupplied(blockPosTemp)) {
                            blockPosTemp = new BlockPos(blockPos.x, blockPos.y - 1, blockPos.z);
                            if (!BlockUtil.isCanBeSupplied(blockPosTemp)) {
                                blockPosTemp = new BlockPos(blockPos.x, blockPos.y, blockPos.z - 1);
                                if (!BlockUtil.isCanBeSupplied(blockPosTemp)) {
                                    blockPosTemp = new BlockPos(blockPos.x, blockPos.y, blockPos.z + 1);
                                    if (!BlockUtil.isCanBeSupplied(blockPosTemp)) {
                                        return;
                                    } else {
                                        placeBlock(blockPosTemp);
                                    }
                                } else {
                                    placeBlock(blockPosTemp);
                                }
                            } else {
                                placeBlock(blockPosTemp);
                            }
                        } else {
                            placeBlock(blockPosTemp);
                        }
                    } else {
                        placeBlock(blockPosTemp);
                    }
                }
                BuildThread3D thread = new BuildThread3D();

                thread.set3DSchematic(1, buildVector, blockPos);

                thread.start();
            }
        }
    }

    private void sendError(String cause) {
        ChatUtils.sendMessage(cause, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);
    }

    private void placeBlock(BlockPos pos) {
        BuildThread3D thread3D = new BuildThread3D();

        thread3D.set3DSchematic(1, buildVector, pos);

        thread3D.start();
    }
}

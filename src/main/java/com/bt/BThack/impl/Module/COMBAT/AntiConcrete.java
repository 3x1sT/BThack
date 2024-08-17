package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.HoleUtil;
import com.bt.BThack.api.Utils.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AntiConcrete extends Module {

    public AntiConcrete() {
        super("AntiConcrete",
                "Placing a button underneath you if you are in a hole to prevent concrete from being thrown at you.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );
    }

    boolean building = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (nullCheck() || building) return;

        BlockPos blockPos = new BlockPos(mc.player.getPositionVector());

        if (HoleUtil.isPlayerInHole(mc.player) && mc.player.collidedVertically && !isButton(mc.world.getBlockState(blockPos).getBlock())) {

            int needSlot = -1;

            for (int i = 0; i < 36; i++) {
                Item item = mc.player.inventory.getStackInSlot(i).getItem();
                if (item instanceof ItemBlock) {
                    ItemBlock itemBlock = (ItemBlock) item;

                    if (isButton(itemBlock.getBlock())) {
                        needSlot = i;
                        break;
                    }
                }
            }

            if (needSlot == -1) return;

            if (needSlot < 9) {
                InventoryUtils.swapItem(needSlot);
            } else {
                InventoryUtils.swapItemOnInventory(mc.player.inventory.currentItem, needSlot);
            }

            ThreadManager.startNewThread(thread -> {
                building = true;
                try {
                    for (int i = 0; i < 3; i++) {
                        BuildManager.buildWithoutChecks(blockPos, thread);
                        BuildManager.delay(50, blockPos, thread);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                building = false;
            });
        }
    }


    private boolean isButton(Block block) {
        return block == Blocks.WOODEN_BUTTON || block == Blocks.STONE_BUTTON;
    }
}

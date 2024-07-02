package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.ItemsUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool",
                "Automatically takes the desired tool into your hand.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );
    }

    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent.LeftClickBlock e) {
        if (nullCheck()) return;

        equipBestSlot(mc.world.getBlockState(e.getPos()));
    }



    private void equipBestSlot(IBlockState blockState) {
        Block block = blockState.getBlock();

        if (ItemPickaxe.EFFECTIVE_ON.contains(block) || block == Blocks.OBSIDIAN)
            equipPickaxe();
        else if (ItemAxe.EFFECTIVE_ON.contains(block)) {
            equipAxe();
        } else if (ItemSpade.EFFECTIVE_ON.contains(block)) {
            equipSpade();
        } else {
            ChatUtils.sendMessage(ChatFormatting.YELLOW + "The module cannot find the correct item for this block. Please contact the creator of BThack about this issue.");
        }
    }


    private void equipPickaxe() {
        for (int needSlot = 0; needSlot < 36; needSlot++) {
            if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemPickaxe) {
                startSwap(needSlot);
                break;
            }
        }
    }
    private void equipAxe() {
        for (int needSlot = 0; needSlot < 36; needSlot++) {
            if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemAxe) {
                startSwap(needSlot);
                break;
            }
        }
    }
    private void equipSpade() {
        for (int needSlot = 0; needSlot < 36; needSlot++) {
            if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemSpade) {
                startSwap(needSlot);
                break;
            }
        }
    }
    private void startSwap(int needSlot) {
        if (needSlot < 9) {
            ItemsUtil.swapItem(needSlot);
        } else {
            mc.playerController.windowClick(0, needSlot, mc.player.inventory.currentItem + 36, ClickType.SWAP, mc.player);
            mc.playerController.updateController();
        }
    }
}

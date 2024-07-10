package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.ItemsUtil;
import com.google.common.collect.Sets;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Set;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool",
                "Automatically takes the desired tool into your hand.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addCheckbox("Report A Problem", this, true);
    }

    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent.LeftClickBlock e) {
        if (nullCheck()) return;

        equipBestSlot(mc.world.getBlockState(e.getPos()));
    }



    public static void equipBestSlot(IBlockState blockState) {
        Block block = blockState.getBlock();

        if (pickaxeEffective.contains(block))
            equipPickaxe();
        else if (axeEffective.contains(block)) {
            equipAxe();
        } else if (spadeEffective.contains(block)) {
            equipSpade();
        } else {
            if (!ignoreBlocks.contains(block)) {
                if (getCheckbox("AutoTool", "Report A Problem"))
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "The module cannot find the correct item for this block(" + block + "). Please contact the creator of BThack about this issue. Turn off the \"Report A Problem\" setting if you do not want to see this message.");
            }
        }
    }


    private static void equipPickaxe() {
        Minecraft mc = Minecraft.getMinecraft();
        for (int needSlot = 0; needSlot < 36; needSlot++) {
            if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemPickaxe) {
                startSwap(needSlot);
                break;
            }
        }
    }
    private static void equipAxe() {
        Minecraft mc = Minecraft.getMinecraft();
        for (int needSlot = 0; needSlot < 36; needSlot++) {
            if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemAxe) {
                startSwap(needSlot);
                break;
            }
        }
    }
    private static void equipSpade() {
        Minecraft mc = Minecraft.getMinecraft();
        for (int needSlot = 0; needSlot < 36; needSlot++) {
            if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemSpade) {
                startSwap(needSlot);
                break;
            }
        }
    }
    private static void startSwap(int needSlot) {
        Minecraft mc = Minecraft.getMinecraft();
        if (needSlot < 9) {
            ItemsUtil.swapItem(needSlot);
        } else {
            mc.playerController.windowClick(0, needSlot, mc.player.inventory.currentItem + 36, ClickType.SWAP, mc.player);
            mc.playerController.updateController();
        }
    }


    private static final Set<Block> pickaxeEffective = Sets.newHashSet(
            Blocks.ACTIVATOR_RAIL,
            Blocks.COAL_ORE,
            Blocks.COBBLESTONE,
            Blocks.DETECTOR_RAIL,
            Blocks.DIAMOND_BLOCK,
            Blocks.DIAMOND_ORE,
            Blocks.DOUBLE_STONE_SLAB,
            Blocks.GOLDEN_RAIL,
            Blocks.GOLD_BLOCK,
            Blocks.GOLD_ORE,
            Blocks.ICE,
            Blocks.IRON_BLOCK,
            Blocks.IRON_ORE,
            Blocks.LAPIS_BLOCK,
            Blocks.LAPIS_ORE,
            Blocks.LIT_REDSTONE_ORE,
            Blocks.MOSSY_COBBLESTONE,
            Blocks.NETHERRACK,
            Blocks.PACKED_ICE,
            Blocks.RAIL,
            Blocks.REDSTONE_ORE,
            Blocks.SANDSTONE,
            Blocks.RED_SANDSTONE,
            Blocks.STONE,
            Blocks.STONE_SLAB,
            Blocks.STONE_BUTTON,
            Blocks.STONE_PRESSURE_PLATE,

            Blocks.OBSIDIAN,
            Blocks.ENDER_CHEST,
            Blocks.ANVIL,
            Blocks.FURNACE,
            Blocks.LIT_FURNACE,
            Blocks.BLACK_SHULKER_BOX,
            Blocks.BLUE_SHULKER_BOX,
            Blocks.BROWN_SHULKER_BOX,
            Blocks.CYAN_SHULKER_BOX,
            Blocks.GRAY_SHULKER_BOX,
            Blocks.GREEN_SHULKER_BOX,
            Blocks.LIGHT_BLUE_SHULKER_BOX,
            Blocks.LIME_SHULKER_BOX,
            Blocks.MAGENTA_SHULKER_BOX,
            Blocks.ORANGE_SHULKER_BOX,
            Blocks.PINK_SHULKER_BOX,
            Blocks.PURPLE_SHULKER_BOX,
            Blocks.RED_SHULKER_BOX,
            Blocks.SILVER_SHULKER_BOX,
            Blocks.WHITE_SHULKER_BOX,
            Blocks.YELLOW_SHULKER_BOX,
            Blocks.ENCHANTING_TABLE,
            Blocks.PISTON,
            Blocks.STICKY_PISTON,
            Blocks.PISTON_EXTENSION,
            Blocks.PISTON_HEAD,
            Blocks.QUARTZ_BLOCK,
            Blocks.QUARTZ_ORE,
            Blocks.QUARTZ_STAIRS,

            Blocks.GLASS,
            Blocks.GLASS_PANE,
            Blocks.STAINED_GLASS,
            Blocks.STAINED_GLASS_PANE
    );

    private static final Set<Block> axeEffective = Sets.newHashSet(
            Blocks.PLANKS,
            Blocks.BOOKSHELF,
            Blocks.LOG,
            Blocks.LOG2,
            Blocks.CHEST,
            Blocks.PUMPKIN,
            Blocks.LIT_PUMPKIN,
            Blocks.MELON_BLOCK,
            Blocks.LADDER,
            Blocks.WOODEN_BUTTON,
            Blocks.WOODEN_PRESSURE_PLATE,

            Blocks.CRAFTING_TABLE
    );

    private static final Set<Block> spadeEffective = Sets.newHashSet(
            Blocks.CLAY,
            Blocks.DIRT,
            Blocks.FARMLAND,
            Blocks.GRASS,
            Blocks.GRAVEL,
            Blocks.MYCELIUM,
            Blocks.SAND,
            Blocks.SNOW,
            Blocks.SNOW_LAYER,
            Blocks.SOUL_SAND,
            Blocks.GRASS_PATH,
            Blocks.CONCRETE_POWDER
    );

    public static final Set<Block> ignoreBlocks = Sets.newHashSet(
            Blocks.BEDROCK,
            Blocks.AIR,
            Blocks.YELLOW_FLOWER,
            Blocks.RED_FLOWER,
            Blocks.FLOWER_POT,
            Blocks.TALLGRASS,
            Blocks.PORTAL,
            Blocks.END_PORTAL,
            Blocks.END_PORTAL_FRAME
    );
}

package com.bt.BThack.api.Storage;

import com.bt.BThack.BThack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class BlocksData {

    public static boolean invalidBlock = false;

    //todo: rewrite in new Mc version

    public static Block getNeedBlock(String name) {
        Block needBlock = null;
        invalidBlock = false;

        switch (name) {
            case "anvil":
                needBlock = Blocks.ANVIL;
                break;
            case "air":
                needBlock = Blocks.AIR;
                break;
            case "activator_rail":
                needBlock = Blocks.ACTIVATOR_RAIL;
                break;
            case "acacia_stairs":
                needBlock = Blocks.ACACIA_STAIRS;
                break;
            case "acacia_fence":
                needBlock = Blocks.ACACIA_FENCE;
                break;
            case "acacia_fence_gate":
                needBlock = Blocks.ACACIA_FENCE_GATE;
                break;
            case "acacia_door":
                needBlock = Blocks.ACACIA_DOOR;
                break;
            case "bookshelf":
                needBlock = Blocks.BOOKSHELF;
                break;
            case "brown_shulker_box":
                needBlock = Blocks.BROWN_SHULKER_BOX;
                break;
            case "bone_block":
                needBlock = Blocks.BONE_BLOCK;
                break;
            case "brick_block":
                needBlock = Blocks.BRICK_BLOCK;
                break;
            case "bed":
                needBlock = Blocks.BED;
                break;
            case "brown_glazed_terracotta":
                needBlock = Blocks.BROWN_GLAZED_TERRACOTTA;
                break;
            case "brick_stairs":
                needBlock = Blocks.BRICK_STAIRS;
                break;
            case "brewing_stand":
                needBlock = Blocks.BREWING_STAND;
                break;
            case "blue_shulker_box":
                needBlock = Blocks.BLUE_SHULKER_BOX;
                break;
            case "blue_glazed_terracotta":
                needBlock = Blocks.BLUE_GLAZED_TERRACOTTA;
                break;
            case "black_shulker_box":
                needBlock = Blocks.BLACK_SHULKER_BOX;
                break;
            case "black_glazed_terracotta":
                needBlock = Blocks.BLACK_GLAZED_TERRACOTTA;
                break;
            case "birch_stairs":
                needBlock = Blocks.BIRCH_STAIRS;
                break;
            case "birch_fence_gate":
                needBlock = Blocks.BIRCH_FENCE_GATE;
                break;
            case "birch_fence":
                needBlock = Blocks.BIRCH_FENCE;
                break;
            case "birch_door":
                needBlock = Blocks.BIRCH_DOOR;
                break;
            case "beetroots":
                needBlock = Blocks.BEETROOTS;
                break;
            case "bedrock":
                needBlock = Blocks.BEDROCK;
                break;
            case "beacon":
                needBlock = Blocks.BEACON;
                break;
            case "barrier":
                needBlock = Blocks.BARRIER;
                break;
            case "brown_mushroom":
                needBlock = Blocks.BROWN_MUSHROOM;
                break;
            case "brown_mushroom_block":
                needBlock = Blocks.BROWN_MUSHROOM_BLOCK;
                break;
            case "cocoa":
                needBlock = Blocks.COCOA;
                break;
            case "cyan_shulker_box":
                needBlock = Blocks.CYAN_SHULKER_BOX;
                break;
            case "coal_block":
                needBlock = Blocks.COAL_BLOCK;
                break;
            case "concrete":
                needBlock = Blocks.CONCRETE;
                break;
            case "command_block":
                needBlock = Blocks.COMMAND_BLOCK;
                break;
            case "cyan_glazed_terracotta":
                needBlock = Blocks.CYAN_GLAZED_TERRACOTTA;
                break;
            case "crafting_table":
                needBlock = Blocks.CRAFTING_TABLE;
                break;
            case "concrete_powder":
                needBlock = Blocks.CONCRETE_POWDER;
                break;
            case "cobblestone_wall":
                needBlock = Blocks.COBBLESTONE_WALL;
                break;
            case "cobblestone":
                needBlock = Blocks.COBBLESTONE;
                break;
            case "coal_ore":
                needBlock = Blocks.COAL_ORE;
                break;
            case "clay":
                needBlock = Blocks.CLAY;
                break;
            case "chorus_plant":
                needBlock = Blocks.CHORUS_PLANT;
                break;
            case "chorus_flower":
                needBlock = Blocks.CHORUS_FLOWER;
                break;
            case "cauldron":
                needBlock = Blocks.CAULDRON;
                break;
            case "carrots":
                needBlock = Blocks.CARROTS;
                break;
            case "carpet":
                needBlock = Blocks.CARPET;
                break;
            case "cake":
                needBlock = Blocks.CAKE;
                break;
            case "cactus":
                needBlock = Blocks.CACTUS;
                break;
            case "chain_command_block":
                needBlock = Blocks.CHAIN_COMMAND_BLOCK;
                break;
            case "chest":
                needBlock = Blocks.CHEST;
                break;
            case "dropper":
                needBlock = Blocks.DROPPER;
                break;
            case "dragon_egg":
                needBlock = Blocks.DRAGON_EGG;
                break;
            case "diamond_block":
                needBlock = Blocks.DIAMOND_BLOCK;
                break;
            case "dispenser":
                needBlock = Blocks.DISPENSER;
                break;
            case "dirt":
                needBlock = Blocks.DIRT;
                break;
            case "double_wooden_slab":
                needBlock = Blocks.DOUBLE_WOODEN_SLAB;
                break;
            case "double_stone_slab2":
                needBlock = Blocks.DOUBLE_STONE_SLAB2;
                break;
            case "double_stone_slab":
                needBlock = Blocks.DOUBLE_STONE_SLAB;
                break;
            case "double_plant":
                needBlock = Blocks.DOUBLE_PLANT;
                break;
            case "detector_rail":
                needBlock = Blocks.DETECTOR_RAIL;
                break;
            case "daylight_detector_inverted":
                needBlock = Blocks.DAYLIGHT_DETECTOR_INVERTED;
                break;
            case "dark_oak_stairs":
                needBlock = Blocks.DARK_OAK_STAIRS;
                break;
            case "dark_oak_fence_gate":
                needBlock = Blocks.DARK_OAK_FENCE_GATE;
                break;
            case "dark_oak_fence":
                needBlock = Blocks.DARK_OAK_FENCE;
                break;
            case "deadbush":
                needBlock = Blocks.DEADBUSH;
                break;
            case "dark_oak_door":
                needBlock = Blocks.DARK_OAK_DOOR;
                break;
            case "diamond_ore":
                needBlock = Blocks.DIAMOND_ORE;
                break;
            case "emerald_block":
                needBlock = Blocks.EMERALD_BLOCK;
                break;
            case "ender_chest":
                needBlock = Blocks.ENDER_CHEST;
                break;
            case "end_stone":
                needBlock = Blocks.END_STONE;
                break;
            case "end_rod":
                needBlock = Blocks.END_ROD;
                break;
            case "end_portal":
                needBlock = Blocks.END_PORTAL;
                break;
            case "end_portal_frame":
                needBlock = Blocks.END_PORTAL_FRAME;
                break;
            case "end_gateway":
                needBlock = Blocks.END_GATEWAY;
                break;
            case "end_bricks":
                needBlock = Blocks.END_BRICKS;
                break;
            case "enchanting_table":
                needBlock = Blocks.ENCHANTING_TABLE;
                break;
            case "emerald_ore":
                needBlock = Blocks.EMERALD_ORE;
                break;
            case "frosted_ice":
                needBlock = Blocks.FROSTED_ICE;
                break;
            case "fire":
                needBlock = Blocks.FIRE;
                break;
            case "furnace":
                needBlock = Blocks.FURNACE;
                break;
            case "farmland":
                needBlock = Blocks.FARMLAND;
                break;
            case "flowing_water":
                needBlock = Blocks.FLOWING_WATER;
                break;
            case "flowing_lava":
                needBlock = Blocks.FLOWING_LAVA;
                break;
            case "flower_pot":
                needBlock = Blocks.FLOWER_POT;
                break;
            case "gold_block":
                needBlock = Blocks.GOLD_BLOCK;
                break;
            case "gravel":
                needBlock = Blocks.GRAVEL;
                break;
            case "green_shulker_box":
                needBlock = Blocks.GREEN_SHULKER_BOX;
                break;
            case "grass":
                needBlock = Blocks.GRASS;
                break;
            case "grass_path":
                needBlock = Blocks.GRASS_PATH;
                break;
            case "green_glazed_terracotta":
                needBlock = Blocks.GREEN_GLAZED_TERRACOTTA;
                break;
            case "gray_shulker_box":
                needBlock = Blocks.GRAY_SHULKER_BOX;
                break;
            case "gray_glazed_terracotta":
                needBlock = Blocks.GRAY_GLAZED_TERRACOTTA;
                break;
            case "golden_rail":
                needBlock = Blocks.GOLDEN_RAIL;
                break;
            case "gold_ore":
                needBlock = Blocks.GOLD_ORE;
                break;
            case "glass_pane":
                needBlock = Blocks.GLASS_PANE;
                break;
            case "glass":
                needBlock = Blocks.GLASS;
                break;
            case "glowstone":
                needBlock = Blocks.GLOWSTONE;
                break;
            case "hay_block":
                needBlock = Blocks.HAY_BLOCK;
                break;
            case "hopper":
                needBlock = Blocks.HOPPER;
                break;
            case "hardened_clay":
                needBlock = Blocks.HARDENED_CLAY;
                break;
            case "heavy_weighted_pressure_plate":
                needBlock = Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE;
                break;
            case "iron_block":
                needBlock = Blocks.IRON_BLOCK;
                break;
            case "ice":
                needBlock = Blocks.ICE;
                break;
            case "iron_trapdoor":
                needBlock = Blocks.IRON_TRAPDOOR;
                break;
            case "iron_ore":
                needBlock = Blocks.IRON_ORE;
                break;
            case "iron_door":
                needBlock = Blocks.IRON_DOOR;
                break;
            case "iron_bars":
                needBlock = Blocks.IRON_BARS;
                break;
            case "jukebox":
                needBlock = Blocks.JUKEBOX;
                break;
            case "jungle_fence":
                needBlock = Blocks.JUNGLE_FENCE;
                break;
            case "jungle_fence_gate":
                needBlock = Blocks.JUNGLE_FENCE_GATE;
                break;
            case "jungle_stairs":
                needBlock = Blocks.JUNGLE_STAIRS;
                break;
            case "jungle_door":
                needBlock = Blocks.JUNGLE_DOOR;
                break;
            case "log":
                needBlock = Blocks.LOG;
                break;
            case "log2":
                needBlock = Blocks.LOG2;
                break;
            case "lapis_block":
                needBlock = Blocks.LAPIS_BLOCK;
                break;
            case "lit_pumpkin":
                needBlock = Blocks.LIT_PUMPKIN;
                break;
            case "lava":
                needBlock = Blocks.LAVA;
                break;
            case "lit_redstone_ore":
                needBlock = Blocks.LIT_REDSTONE_ORE;
                break;
            case "lit_redstone_lamp":
                needBlock = Blocks.LIT_REDSTONE_LAMP;
                break;
            case "lit_furnace":
                needBlock = Blocks.LIT_FURNACE;
                break;
            case "lime_shulker_box":
                needBlock = Blocks.LIME_SHULKER_BOX;
                break;
            case "lime_glazed_terracotta":
                needBlock = Blocks.LIME_GLAZED_TERRACOTTA;
                break;
            case "light_weighted_pressure_plate":
                needBlock = Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE;
                break;
            case "light_blue_shulker_box":
                needBlock = Blocks.LIGHT_BLUE_SHULKER_BOX;
                break;
            case "light_blue_glazed_terracotta":
                needBlock = Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA;
                break;
            case "leaves2":
                needBlock = Blocks.LEAVES2;
                break;
            case "leaves":
                needBlock = Blocks.LEAVES;
                break;
            case "lapis_ore":
                needBlock = Blocks.LAPIS_ORE;
                break;
            case "lever":
                needBlock = Blocks.LEVER;
                break;
            case "ladder":
                needBlock = Blocks.LADDER;
                break;
            case "melon_block":
                needBlock = Blocks.MELON_BLOCK;
                break;
            case "mossy_cobblestone":
                needBlock = Blocks.MOSSY_COBBLESTONE;
                break;
            case "mycelium":
                needBlock = Blocks.MYCELIUM;
                break;
            case "magma":
                needBlock = Blocks.MAGMA;
                break;
            case "mob_spawner":
                needBlock = Blocks.MOB_SPAWNER;
                break;
            case "magenta_shulker_box":
                needBlock = Blocks.MAGENTA_SHULKER_BOX;
                break;
            case "magenta_glazed_terracotta":
                needBlock = Blocks.MAGENTA_GLAZED_TERRACOTTA;
                break;
            case "melon_stem":
                needBlock = Blocks.MELON_STEM;
                break;
            case "monster_egg":
                needBlock = Blocks.MONSTER_EGG;
                break;
            case "netherrack":
                needBlock = Blocks.NETHERRACK;
                break;
            case "nether_wart_block":
                needBlock = Blocks.NETHER_WART_BLOCK;
                break;
            case "noteblock":
                needBlock = Blocks.NOTEBLOCK;
                break;
            case "nether_wart":
                needBlock = Blocks.NETHER_WART;
                break;
            case "nether_brick":
                needBlock = Blocks.NETHER_BRICK;
                break;
            case "nether_brick_stairs":
                needBlock = Blocks.NETHER_BRICK_STAIRS;
                break;
            case "nether_brick_fence":
                needBlock = Blocks.NETHER_BRICK_FENCE;
                break;
            case "obsidian":
                needBlock = Blocks.OBSIDIAN;
                break;
            case "orange_shulker_box":
                needBlock = Blocks.ORANGE_SHULKER_BOX;
                break;
            case "observer":
                needBlock = Blocks.OBSERVER;
                break;
            case "oak_fence":
                needBlock = Blocks.OAK_FENCE;
                break;
            case "oak_stairs":
                needBlock = Blocks.OAK_STAIRS;
                break;
            case "orange_glazed_terracotta":
                needBlock = Blocks.ORANGE_GLAZED_TERRACOTTA;
                break;
            case "oak_fence_gate":
                needBlock = Blocks.OAK_FENCE_GATE;
                break;
            case "oak_door":
                needBlock = Blocks.OAK_DOOR;
                break;
            case "purpur_stairs":
                needBlock = Blocks.PURPUR_STAIRS;
                break;
            case "purpur_slab":
                needBlock = Blocks.PURPUR_SLAB;
                break;
            case "purpur_block":
                needBlock = Blocks.PURPUR_BLOCK;
                break;
            case "purpur_pillar":
                needBlock = Blocks.PURPUR_PILLAR;
                break;
            case "prismarine":
                needBlock = Blocks.PRISMARINE;
                break;
            case "purpur_double_slab":
                needBlock = Blocks.PURPUR_DOUBLE_SLAB;
                break;
            case "purple_shulker_box":
                needBlock = Blocks.PURPLE_SHULKER_BOX;
                break;
            case "purple_glazed_terracotta":
                needBlock = Blocks.PURPLE_GLAZED_TERRACOTTA;
                break;
            case "pumpkin_stem":
                needBlock = Blocks.PUMPKIN_STEM;
                break;
            case "pumpkin":
                needBlock = Blocks.PUMPKIN;
                break;
            case "powered_repeater":
                needBlock = Blocks.POWERED_REPEATER;
                break;
            case "powered_comparator":
                needBlock = Blocks.POWERED_COMPARATOR;
                break;
            case "potatoes":
                needBlock = Blocks.POTATOES;
                break;
            case "piston_head":
                needBlock = Blocks.PISTON_HEAD;
                break;
            case "piston":
                needBlock = Blocks.PISTON;
                break;
            case "piston_extension":
                needBlock = Blocks.PISTON_EXTENSION;
                break;
            case "pink_shulker_box":
                needBlock = Blocks.PINK_SHULKER_BOX;
                break;
            case "pink_glazed_terracotta":
                needBlock = Blocks.PINK_GLAZED_TERRACOTTA;
                break;
            case "portal":
                needBlock = Blocks.PORTAL;
                break;
            case "planks":
                needBlock = Blocks.PLANKS;
                break;
            case "packet_ice":
                needBlock = Blocks.PACKED_ICE;
                break;
            case "quartz_block":
                needBlock = Blocks.QUARTZ_BLOCK;
                break;
            case "quartz_stairs":
                needBlock = Blocks.QUARTZ_STAIRS;
                break;
            case "quartz_ore":
                needBlock = Blocks.QUARTZ_ORE;
                break;
            case "reeds":
                needBlock = Blocks.REEDS;
                break;
            case "redstone_block":
                needBlock = Blocks.REDSTONE_BLOCK;
                break;
            case "redstone_wire":
                needBlock = Blocks.REDSTONE_WIRE;
                break;
            case "redstone_torch":
                needBlock = Blocks.REDSTONE_TORCH;
                break;
            case "redstone_ore":
                needBlock = Blocks.REDSTONE_ORE;
                break;
            case "redstone_lamp":
                needBlock = Blocks.REDSTONE_LAMP;
                break;
            case "red_shulker_box":
                needBlock = Blocks.RED_SHULKER_BOX;
                break;
            case "red_sandstone_stairs":
                needBlock = Blocks.RED_SANDSTONE_STAIRS;
                break;
            case "red_sandstone":
                needBlock = Blocks.RED_SANDSTONE;
                break;
            case "red_nether_brick":
                needBlock = Blocks.RED_NETHER_BRICK;
                break;
            case "red_glazed_terracotta":
                needBlock = Blocks.RED_GLAZED_TERRACOTTA;
                break;
            case "red_flower":
                needBlock = Blocks.RED_FLOWER;
                break;
            case "red_mushroom":
                needBlock = Blocks.RED_MUSHROOM;
                break;
            case "repeating_command_block":
                needBlock = Blocks.REPEATING_COMMAND_BLOCK;
                break;
            case "rail":
                needBlock = Blocks.RAIL;
                break;
            case "red_mushroom_block":
                needBlock = Blocks.RED_MUSHROOM_BLOCK;
                break;
            case "stonebrick":
                needBlock = Blocks.STONEBRICK;
                break;
            case "stone":
                needBlock = Blocks.STONE;
                break;
            case "structure_void":
                needBlock = Blocks.STRUCTURE_VOID;
                break;
            case "stone_stairs":
                needBlock = Blocks.STONE_STAIRS;
                break;
            case "slime_block":
                needBlock = Blocks.SLIME_BLOCK;
                break;
            case "stone_slab2":
                needBlock = Blocks.STONE_SLAB2;
                break;
            case "stone_slab":
                needBlock = Blocks.STONE_SLAB;
                break;
            case "stone_pressure_plate":
                needBlock = Blocks.STONE_PRESSURE_PLATE;
                break;
            case "stone_button":
                needBlock = Blocks.STONE_BUTTON;
                break;
            case "stone_brick_stairs":
                needBlock = Blocks.STONE_BRICK_STAIRS;
                break;
            case "sticky_piston":
                needBlock = Blocks.STICKY_PISTON;
                break;
            case "standing_sign":
                needBlock = Blocks.STANDING_SIGN;
                break;
            case "standing_banner":
                needBlock = Blocks.STANDING_BANNER;
                break;
            case "stained_hardened_clay":
                needBlock = Blocks.STAINED_HARDENED_CLAY;
                break;
            case "stained_glass_pane":
                needBlock = Blocks.STAINED_GLASS_PANE;
                break;
            case "stained_glass":
                needBlock = Blocks.STAINED_GLASS;
                break;
            case "spruce_stairs":
                needBlock = Blocks.SPRUCE_STAIRS;
                break;
            case "spruce_fence_gate":
                needBlock = Blocks.SPRUCE_FENCE_GATE;
                break;
            case "spruce_fence":
                needBlock = Blocks.SPRUCE_FENCE;
                break;
            case "spruce_door":
                needBlock = Blocks.SPRUCE_DOOR;
                break;
            case "sponge":
                needBlock = Blocks.SPONGE;
                break;
            case "soul_sand":
                needBlock = Blocks.SOUL_SAND;
                break;
            case "snow_layer":
                needBlock = Blocks.SNOW_LAYER;
                break;
            case "snow":
                needBlock = Blocks.SNOW;
                break;
            case "skull":
                needBlock = Blocks.SKULL;
                break;
            case "silver_shulker_box":
                needBlock = Blocks.SILVER_SHULKER_BOX;
                break;
            case "silver_glazed_terracotta":
                needBlock = Blocks.SILVER_GLAZED_TERRACOTTA;
                break;
            case "sea_lantern":
                needBlock = Blocks.SEA_LANTERN;
                break;
            case "sandstone_stairs":
                needBlock = Blocks.SANDSTONE_STAIRS;
                break;
            case "sandstone":
                needBlock = Blocks.SANDSTONE;
                break;
            case "sapling":
                needBlock = Blocks.SAPLING;
                break;
            case "structure_block":
                needBlock = Blocks.STRUCTURE_BLOCK;
                break;
            case "tnt":
                needBlock = Blocks.TNT;
                break;
            case "tripwire":
                needBlock = Blocks.TRIPWIRE;
                break;
            case "torch":
                needBlock = Blocks.TORCH;
                break;
            case "trapdoor":
                needBlock = Blocks.TRAPDOOR;
                break;
            case "tallgrass":
                needBlock = Blocks.TALLGRASS;
                break;
            case "tripwire_hook":
                needBlock = Blocks.TRIPWIRE_HOOK;
                break;
            case "trapped_chest":
                needBlock = Blocks.TRAPPED_CHEST;
                break;
            case "unpowered_repeater":
                needBlock = Blocks.UNPOWERED_REPEATER;
                break;
            case "unpowered_comparator":
                needBlock = Blocks.UNPOWERED_COMPARATOR;
                break;
            case "unlit_redstone_torch":
                needBlock = Blocks.UNLIT_REDSTONE_TORCH;
                break;
            case "vine":
                needBlock = Blocks.VINE;
                break;
            case "wool":
                needBlock = Blocks.WOOL;
                break;
            case "web":
                needBlock = Blocks.WEB;
                break;
            case "wooden_slab":
                needBlock = Blocks.WOODEN_SLAB;
                break;
            case "wooden_button":
                needBlock = Blocks.WOODEN_BUTTON;
                break;
            case "wheat":
                needBlock = Blocks.WHEAT;
                break;
            case "wooden_pressure_plate":
                needBlock = Blocks.WOODEN_PRESSURE_PLATE;
                break;
            case "white_shulker_box":
                needBlock = Blocks.WHITE_SHULKER_BOX;
                break;
            case "white_glazed_terracotta":
                needBlock = Blocks.WHITE_GLAZED_TERRACOTTA;
                break;
            case "wall_sign":
                needBlock = Blocks.WALL_SIGN;
                break;
            case "waterlily":
                needBlock = Blocks.WATERLILY;
                break;
            case "water":
                needBlock = Blocks.WATER;
                break;
            case "wall_banner":
                needBlock = Blocks.WALL_BANNER;
                break;
            case "yellow_shulker_box":
                needBlock = Blocks.YELLOW_SHULKER_BOX;
                break;
            case "yellow_flower":
                needBlock = Blocks.YELLOW_FLOWER;
                break;
            case "yellow_glazed_terracotta":
                needBlock = Blocks.YELLOW_GLAZED_TERRACOTTA;
                break;
            default:
                BThack.error("Try get invalid block!");
                invalidBlock = true;
        }

        return needBlock;
    }
}

package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import net.minecraft.block.*;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.input.Keyboard;

public class BHMaximizer extends Module {
    public BHMaximizer() {
        super("BHMaximizer",
                "Increases the hitboxes (AxisAlignedBB) of incomplete blocks to full blocks.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );
    }

    private final AxisAlignedBB axis = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

    @Override
    public void onEnable() {
        BlockEndRod.END_ROD_VERTICAL_AABB = axis;
        BlockEndRod.END_ROD_NS_AABB = axis;
        BlockEndRod.END_ROD_EW_AABB = axis;

        BlockAnvil.X_AXIS_AABB = axis;
        BlockAnvil.Z_AXIS_AABB = axis;

        BlockBed.BED_AABB = axis;

        BlockBrewingStand.BASE_AABB = axis;
        BlockBrewingStand.STICK_AABB = axis;

        BlockCake.CAKE_AABB = new AxisAlignedBB[]{axis, axis, axis, axis, axis, axis, axis};

        BlockChest.NORTH_CHEST_AABB = axis;
        BlockChest.SOUTH_CHEST_AABB = axis;
        BlockChest.WEST_CHEST_AABB = axis;
        BlockChest.EAST_CHEST_AABB = axis;
        BlockChest.NOT_CONNECTED_AABB = axis;

        BlockCocoa.COCOA_EAST_AABB = new AxisAlignedBB[]{axis,axis,axis};
        BlockCocoa.COCOA_WEST_AABB = new AxisAlignedBB[]{axis,axis,axis};
        BlockCocoa.COCOA_NORTH_AABB = new AxisAlignedBB[]{axis,axis,axis};
        BlockCocoa.COCOA_SOUTH_AABB = new AxisAlignedBB[]{axis,axis,axis};

        BlockDaylightDetector.DAYLIGHT_DETECTOR_AABB = axis;

        BlockDoor.SOUTH_AABB = axis;
        BlockDoor.NORTH_AABB = axis;
        BlockDoor.WEST_AABB = axis;
        BlockDoor.EAST_AABB = axis;

        BlockDragonEgg.DRAGON_EGG_AABB = axis;

        BlockEnchantmentTable.AABB = axis;

        BlockEnderChest.ENDER_CHEST_AABB = axis;

        BlockEndPortal.END_PORTAL_AABB = axis;

        BlockEndPortalFrame.AABB_BLOCK = axis;

        BlockFarmland.FARMLAND_AABB = axis;

        BlockFence.BOUNDING_BOXES = new AxisAlignedBB[]{axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis};
        BlockFence.PILLAR_AABB = axis;
        BlockFence.SOUTH_AABB = axis;
        BlockFence.WEST_AABB = axis;
        BlockFence.NORTH_AABB = axis;
        BlockFence.EAST_AABB = axis;

        BlockFenceGate.AABB_HITBOX_ZAXIS = axis;
        BlockFenceGate.AABB_HITBOX_XAXIS = axis;
        BlockFenceGate.AABB_HITBOX_ZAXIS_INWALL = axis;
        BlockFenceGate.AABB_HITBOX_XAXIS_INWALL = axis;
        BlockFenceGate.AABB_COLLISION_BOX_ZAXIS = axis;
        BlockFenceGate.AABB_COLLISION_BOX_XAXIS = axis;

        BlockGrassPath.GRASS_PATH_AABB = axis;

        BlockHopper.BASE_AABB = axis;
        BlockHopper.SOUTH_AABB = axis;
        BlockHopper.NORTH_AABB = axis;
        BlockHopper.WEST_AABB = axis;
        BlockHopper.EAST_AABB = axis;

        BlockPane.AABB_BY_INDEX = new AxisAlignedBB[]{axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis};

        BlockPortal.X_AABB = axis;
        BlockPortal.Z_AABB = axis;
        BlockPortal.Y_AABB = axis;

        BlockRedstoneDiode.REDSTONE_DIODE_AABB = axis;

        BlockSign.SIGN_AABB = axis;

        BlockSkull.DEFAULT_AABB = axis;
        BlockSkull.NORTH_AABB = axis;
        BlockSkull.SOUTH_AABB = axis;
        BlockSkull.WEST_AABB = axis;
        BlockSkull.EAST_AABB = axis;

        BlockSlab.AABB_BOTTOM_HALF = axis;
        BlockSlab.AABB_TOP_HALF = axis;

        BlockSnow.SNOW_AABB = new AxisAlignedBB[]{axis,axis,axis,axis,axis,axis,axis,axis,axis};

        BlockSoulSand.SOUL_SAND_AABB = axis;

        BlockStructureVoid.STRUCTURE_VOID_AABB = axis;

        BlockTrapDoor.EAST_OPEN_AABB = axis;
        BlockTrapDoor.WEST_OPEN_AABB = axis;
        BlockTrapDoor.SOUTH_OPEN_AABB = axis;
        BlockTrapDoor.NORTH_OPEN_AABB = axis;
        BlockTrapDoor.BOTTOM_AABB = axis;
        BlockTrapDoor.TOP_AABB = axis;

        BlockTripWire.AABB = axis;
        BlockTripWire.TRIP_WRITE_ATTACHED_AABB = axis;

        BlockTripWireHook.HOOK_NORTH_AABB = axis;
        BlockTripWireHook.HOOK_SOUTH_AABB = axis;
        BlockTripWireHook.HOOK_WEST_AABB = axis;
        BlockTripWireHook.HOOK_EAST_AABB = axis;

        BlockVine.UP_AABB = axis;
        BlockVine.WEST_AABB = axis;
        BlockVine.EAST_AABB = axis;
        BlockVine.NORTH_AABB = axis;
        BlockVine.SOUTH_AABB = axis;

        BlockWall.AABB_BY_INDEX = new AxisAlignedBB[]{axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis};
        BlockWall.CLIP_AABB_BY_INDEX = new AxisAlignedBB[]{axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis,axis};

        BlockWallSign.SIGN_EAST_AABB = axis;
        BlockWallSign.SIGN_WEST_AABB = axis;
        BlockWallSign.SIGN_SOUTH_AABB = axis;
        BlockWallSign.SIGN_NORTH_AABB = axis;


    }

    @Override
    public void onDisable() {
        BlockEndRod.END_ROD_VERTICAL_AABB = new AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 1.0, 0.625);
        BlockEndRod.END_ROD_NS_AABB = new AxisAlignedBB(0.375, 0.375, 0.0, 0.625, 0.625, 1.0);
        BlockEndRod.END_ROD_EW_AABB = new AxisAlignedBB(0.0, 0.375, 0.375, 1.0, 0.625, 0.625);

        BlockAnvil.X_AXIS_AABB = new AxisAlignedBB(0.0, 0.0, 0.125, 1.0, 1.0, 0.875);
        BlockAnvil.Z_AXIS_AABB = new AxisAlignedBB(0.125, 0.0, 0.0, 0.875, 1.0, 1.0);

        BlockBed.BED_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5625, 1.0);

        BlockBrewingStand.BASE_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
        BlockBrewingStand.STICK_AABB = new AxisAlignedBB(0.4375, 0.0, 0.4375, 0.5625, 0.875, 0.5625);

        BlockCake.CAKE_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.5, 0.9375), new AxisAlignedBB(0.1875, 0.0, 0.0625, 0.9375, 0.5, 0.9375), new AxisAlignedBB(0.3125, 0.0, 0.0625, 0.9375, 0.5, 0.9375), new AxisAlignedBB(0.4375, 0.0, 0.0625, 0.9375, 0.5, 0.9375), new AxisAlignedBB(0.5625, 0.0, 0.0625, 0.9375, 0.5, 0.9375), new AxisAlignedBB(0.6875, 0.0, 0.0625, 0.9375, 0.5, 0.9375), new AxisAlignedBB(0.8125, 0.0, 0.0625, 0.9375, 0.5, 0.9375)};

        BlockChest.NORTH_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0, 0.9375, 0.875, 0.9375);
        BlockChest.SOUTH_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 1.0);
        BlockChest.WEST_CHEST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
        BlockChest.EAST_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 1.0, 0.875, 0.9375);
        BlockChest.NOT_CONNECTED_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);

        BlockCocoa.COCOA_EAST_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.6875, 0.4375, 0.375, 0.9375, 0.75, 0.625), new AxisAlignedBB(0.5625, 0.3125, 0.3125, 0.9375, 0.75, 0.6875), new AxisAlignedBB(0.4375, 0.1875, 0.25, 0.9375, 0.75, 0.75)};
        BlockCocoa.COCOA_WEST_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.0625, 0.4375, 0.375, 0.3125, 0.75, 0.625), new AxisAlignedBB(0.0625, 0.3125, 0.3125, 0.4375, 0.75, 0.6875), new AxisAlignedBB(0.0625, 0.1875, 0.25, 0.5625, 0.75, 0.75)};
        BlockCocoa.COCOA_NORTH_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.375, 0.4375, 0.0625, 0.625, 0.75, 0.3125), new AxisAlignedBB(0.3125, 0.3125, 0.0625, 0.6875, 0.75, 0.4375), new AxisAlignedBB(0.25, 0.1875, 0.0625, 0.75, 0.75, 0.5625)};
        BlockCocoa.COCOA_SOUTH_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.375, 0.4375, 0.6875, 0.625, 0.75, 0.9375), new AxisAlignedBB(0.3125, 0.3125, 0.5625, 0.6875, 0.75, 0.9375), new AxisAlignedBB(0.25, 0.1875, 0.4375, 0.75, 0.75, 0.9375)};

        BlockDaylightDetector.DAYLIGHT_DETECTOR_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.375, 1.0);

        BlockDoor.SOUTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.1875);
        BlockDoor.NORTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.8125, 1.0, 1.0, 1.0);
        BlockDoor.WEST_AABB = new AxisAlignedBB(0.8125, 0.0, 0.0, 1.0, 1.0, 1.0);
        BlockDoor.EAST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.1875, 1.0, 1.0);

        BlockDragonEgg.DRAGON_EGG_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 1.0, 0.9375);

        BlockEnchantmentTable.AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.75, 1.0);

        BlockEnderChest.ENDER_CHEST_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);

        BlockEndPortal.END_PORTAL_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.75, 1.0);

        BlockEndPortalFrame.AABB_BLOCK = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.8125, 1.0);

        BlockFarmland.FARMLAND_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0);

        BlockFence.BOUNDING_BOXES = new AxisAlignedBB[]{new AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 1.0, 0.625), new AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.375, 0.625, 1.0, 0.625), new AxisAlignedBB(0.0, 0.0, 0.375, 0.625, 1.0, 1.0), new AxisAlignedBB(0.375, 0.0, 0.0, 0.625, 1.0, 0.625), new AxisAlignedBB(0.375, 0.0, 0.0, 0.625, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 0.625, 1.0, 0.625), new AxisAlignedBB(0.0, 0.0, 0.0, 0.625, 1.0, 1.0), new AxisAlignedBB(0.375, 0.0, 0.375, 1.0, 1.0, 0.625), new AxisAlignedBB(0.375, 0.0, 0.375, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.375, 1.0, 1.0, 0.625), new AxisAlignedBB(0.0, 0.0, 0.375, 1.0, 1.0, 1.0), new AxisAlignedBB(0.375, 0.0, 0.0, 1.0, 1.0, 0.625), new AxisAlignedBB(0.375, 0.0, 0.0, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.625), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)};
        BlockFence.PILLAR_AABB = new AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 1.5, 0.625);
        BlockFence.SOUTH_AABB = new AxisAlignedBB(0.375, 0.0, 0.625, 0.625, 1.5, 1.0);
        BlockFence.WEST_AABB = new AxisAlignedBB(0.0, 0.0, 0.375, 0.375, 1.5, 0.625);
        BlockFence.NORTH_AABB = new AxisAlignedBB(0.375, 0.0, 0.0, 0.625, 1.5, 0.375);
        BlockFence.EAST_AABB = new AxisAlignedBB(0.625, 0.0, 0.375, 1.0, 1.5, 0.625);

        BlockFenceGate.AABB_HITBOX_ZAXIS = new AxisAlignedBB(0.0, 0.0, 0.375, 1.0, 1.0, 0.625);
        BlockFenceGate.AABB_HITBOX_XAXIS = new AxisAlignedBB(0.375, 0.0, 0.0, 0.625, 1.0, 1.0);
        BlockFenceGate.AABB_HITBOX_ZAXIS_INWALL = new AxisAlignedBB(0.0, 0.0, 0.375, 1.0, 0.8125, 0.625);
        BlockFenceGate.AABB_HITBOX_XAXIS_INWALL = new AxisAlignedBB(0.375, 0.0, 0.0, 0.625, 0.8125, 1.0);
        BlockFenceGate.AABB_COLLISION_BOX_ZAXIS = new AxisAlignedBB(0.0, 0.0, 0.375, 1.0, 1.5, 0.625);
        BlockFenceGate.AABB_COLLISION_BOX_XAXIS = new AxisAlignedBB(0.375, 0.0, 0.0, 0.625, 1.5, 1.0);

        BlockGrassPath.GRASS_PATH_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0);

        BlockHopper.BASE_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.625, 1.0);
        BlockHopper.SOUTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.125);
        BlockHopper.NORTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.875, 1.0, 1.0, 1.0);
        BlockHopper.WEST_AABB = new AxisAlignedBB(0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
        BlockHopper.EAST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.125, 1.0, 1.0);

        BlockPane.AABB_BY_INDEX = new AxisAlignedBB[]{new AxisAlignedBB(0.4375, 0.0, 0.4375, 0.5625, 1.0, 0.5625), new AxisAlignedBB(0.4375, 0.0, 0.4375, 0.5625, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.4375, 0.5625, 1.0, 0.5625), new AxisAlignedBB(0.0, 0.0, 0.4375, 0.5625, 1.0, 1.0), new AxisAlignedBB(0.4375, 0.0, 0.0, 0.5625, 1.0, 0.5625), new AxisAlignedBB(0.4375, 0.0, 0.0, 0.5625, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 0.5625, 1.0, 0.5625), new AxisAlignedBB(0.0, 0.0, 0.0, 0.5625, 1.0, 1.0), new AxisAlignedBB(0.4375, 0.0, 0.4375, 1.0, 1.0, 0.5625), new AxisAlignedBB(0.4375, 0.0, 0.4375, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.4375, 1.0, 1.0, 0.5625), new AxisAlignedBB(0.0, 0.0, 0.4375, 1.0, 1.0, 1.0), new AxisAlignedBB(0.4375, 0.0, 0.0, 1.0, 1.0, 0.5625), new AxisAlignedBB(0.4375, 0.0, 0.0, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5625), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)};

        BlockPortal.X_AABB = new AxisAlignedBB(0.0, 0.0, 0.375, 1.0, 1.0, 0.625);
        BlockPortal.Z_AABB = new AxisAlignedBB(0.375, 0.0, 0.0, 0.625, 1.0, 1.0);
        BlockPortal.Y_AABB = new AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 1.0, 0.625);

        BlockRedstoneDiode.REDSTONE_DIODE_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);

        BlockSign.SIGN_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);

        BlockSkull.DEFAULT_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
        BlockSkull.NORTH_AABB = new AxisAlignedBB(0.25, 0.25, 0.5, 0.75, 0.75, 1.0);
        BlockSkull.SOUTH_AABB = new AxisAlignedBB(0.25, 0.25, 0.0, 0.75, 0.75, 0.5);
        BlockSkull.WEST_AABB = new AxisAlignedBB(0.5, 0.25, 0.25, 1.0, 0.75, 0.75);
        BlockSkull.EAST_AABB = new AxisAlignedBB(0.0, 0.25, 0.25, 0.5, 0.75, 0.75);

        BlockSlab.AABB_BOTTOM_HALF = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
        BlockSlab.AABB_TOP_HALF = new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);

        BlockSnow.SNOW_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.375, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.625, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.75, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)};

        BlockSoulSand.SOUL_SAND_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0);

        BlockStructureVoid.STRUCTURE_VOID_AABB = new AxisAlignedBB(0.3, 0.3, 0.3, 0.7, 0.7, 0.7);

        BlockTrapDoor.EAST_OPEN_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.1875, 1.0, 1.0);
        BlockTrapDoor.WEST_OPEN_AABB = new AxisAlignedBB(0.8125, 0.0, 0.0, 1.0, 1.0, 1.0);
        BlockTrapDoor.SOUTH_OPEN_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.1875);
        BlockTrapDoor.NORTH_OPEN_AABB = new AxisAlignedBB(0.0, 0.0, 0.8125, 1.0, 1.0, 1.0);
        BlockTrapDoor.BOTTOM_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.1875, 1.0);
        BlockTrapDoor.TOP_AABB = new AxisAlignedBB(0.0, 0.8125, 0.0, 1.0, 1.0, 1.0);

        BlockTripWire.AABB = new AxisAlignedBB(0.0, 0.0625, 0.0, 1.0, 0.15625, 1.0);
        BlockTripWire.TRIP_WRITE_ATTACHED_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);

        BlockTripWireHook.HOOK_NORTH_AABB = new AxisAlignedBB(0.3125, 0.0, 0.625, 0.6875, 0.625, 1.0);
        BlockTripWireHook.HOOK_SOUTH_AABB = new AxisAlignedBB(0.3125, 0.0, 0.0, 0.6875, 0.625, 0.375);
        BlockTripWireHook.HOOK_WEST_AABB = new AxisAlignedBB(0.625, 0.0, 0.3125, 1.0, 0.625, 0.6875);
        BlockTripWireHook.HOOK_EAST_AABB = new AxisAlignedBB(0.0, 0.0, 0.3125, 0.375, 0.625, 0.6875);

        BlockVine.UP_AABB = new AxisAlignedBB(0.0, 0.9375, 0.0, 1.0, 1.0, 1.0);
        BlockVine.WEST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0625, 1.0, 1.0);
        BlockVine.EAST_AABB = new AxisAlignedBB(0.9375, 0.0, 0.0, 1.0, 1.0, 1.0);
        BlockVine.NORTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.0625);
        BlockVine.SOUTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.9375, 1.0, 1.0, 1.0);

        BlockWall.AABB_BY_INDEX = new AxisAlignedBB[]{new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.0, 0.75), new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.25, 0.75, 1.0, 0.75), new AxisAlignedBB(0.0, 0.0, 0.25, 0.75, 1.0, 1.0), new AxisAlignedBB(0.25, 0.0, 0.0, 0.75, 1.0, 0.75), new AxisAlignedBB(0.3125, 0.0, 0.0, 0.6875, 0.875, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 0.75, 1.0, 0.75), new AxisAlignedBB(0.0, 0.0, 0.0, 0.75, 1.0, 1.0), new AxisAlignedBB(0.25, 0.0, 0.25, 1.0, 1.0, 0.75), new AxisAlignedBB(0.25, 0.0, 0.25, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.3125, 1.0, 0.875, 0.6875), new AxisAlignedBB(0.0, 0.0, 0.25, 1.0, 1.0, 1.0), new AxisAlignedBB(0.25, 0.0, 0.0, 1.0, 1.0, 0.75), new AxisAlignedBB(0.25, 0.0, 0.0, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.75), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)};
        BlockWall.CLIP_AABB_BY_INDEX = new AxisAlignedBB[]{BlockWall.AABB_BY_INDEX[0].setMaxY(1.5), BlockWall.AABB_BY_INDEX[1].setMaxY(1.5), BlockWall.AABB_BY_INDEX[2].setMaxY(1.5), BlockWall.AABB_BY_INDEX[3].setMaxY(1.5), BlockWall.AABB_BY_INDEX[4].setMaxY(1.5), BlockWall.AABB_BY_INDEX[5].setMaxY(1.5), BlockWall.AABB_BY_INDEX[6].setMaxY(1.5), BlockWall.AABB_BY_INDEX[7].setMaxY(1.5), BlockWall.AABB_BY_INDEX[8].setMaxY(1.5), BlockWall.AABB_BY_INDEX[9].setMaxY(1.5), BlockWall.AABB_BY_INDEX[10].setMaxY(1.5), BlockWall.AABB_BY_INDEX[11].setMaxY(1.5), BlockWall.AABB_BY_INDEX[12].setMaxY(1.5), BlockWall.AABB_BY_INDEX[13].setMaxY(1.5), BlockWall.AABB_BY_INDEX[14].setMaxY(1.5), BlockWall.AABB_BY_INDEX[15].setMaxY(1.5)};

        BlockWallSign.SIGN_EAST_AABB = new AxisAlignedBB(0.0, 0.28125, 0.0, 0.125, 0.78125, 1.0);
        BlockWallSign.SIGN_WEST_AABB = new AxisAlignedBB(0.875, 0.28125, 0.0, 1.0, 0.78125, 1.0);
        BlockWallSign.SIGN_SOUTH_AABB = new AxisAlignedBB(0.0, 0.28125, 0.0, 1.0, 0.78125, 0.125);
        BlockWallSign.SIGN_NORTH_AABB = new AxisAlignedBB(0.0, 0.28125, 0.875, 1.0, 0.78125, 1.0);


    }
}
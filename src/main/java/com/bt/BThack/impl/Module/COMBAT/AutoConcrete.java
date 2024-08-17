package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.Build.BuildThread3D;
import com.bt.BThack.api.Utils.HoleUtil;
import com.bt.BThack.api.Utils.InventoryUtils;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.bt.BThack.api.Utils.Modules.KillAuraUtils;
import net.minecraft.block.BlockConcretePowder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;


import java.util.*;

public class AutoConcrete extends Module {

    public AutoConcrete() {
        super("AutoConcrete",
                "Automatically places concrete.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        addSlider("Distance", this, 4, 3, 4.5, false);
        addSlider("Place Delay", this, 1, 1, 3, true);
        addSlider("Swap Delay(MS)", this, 0, 0, 100, true);

        addSlider("Concretes", this, 1, 1, 2, true);

        addCheckbox("One Hole Safe", this, true);

        addCheckbox("Friends", this, false);
        addCheckbox("Teammates", this, false);
        ClansUtils.addClanManagerInModule(this);

        addCheckbox("Only Hotbar", this, false);
        addMode("ConcreteB Mode", this, new ArrayList<>(Arrays.asList("First", "Last")));
        addMode("SafeB Mode", this, new ArrayList<>(Arrays.asList("First", "Last")));
        addCheckbox("Obsidian SafeB Only", this, false);
        addCheckbox("Concrete In SafeB", this, true);
    }

    Set<BlockPos> concretePositions = new HashSet<>();

    boolean placing = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) {
            toggle();
            return;
        }
        if (placing) return;

        EntityPlayer player = mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer != mc.player && !KillAuraUtils.isFriend(entityPlayer, this.name) && !KillAuraUtils.isTeammate(entityPlayer, this.name) && KillAuraUtils.isSuccessfulClanMember(entityPlayer, this.name) && entityPlayer.isEntityAlive() && HoleUtil.isPlayerInHole(entityPlayer) && !concretePositions.contains(new BlockPos(entityPlayer.getPositionVector())) && mc.world.getBlockState(new BlockPos(entityPlayer.getPositionVector())).getBlock() != Blocks.CONCRETE_POWDER).min(Comparator.comparing(entityPlayer ->
                entityPlayer.getDistance(mc.player))).filter(entityPlayer -> entityPlayer.getDistance(mc.player) <= getSlider(this.name, "Distance")).orElse(null);

        if (player != null) {
            int concreteSlot = -1;
            int safeBlockSlot = -1;

            BlockPos playerPos = new BlockPos(player.getPositionVector());

            if (getCheckbox(this.name, "One Hole Safe")) {
                if (playerPos.equals(new BlockPos(mc.player.getPositionVector()))) return;
            }

            boolean concreteFinded = false;
            boolean safeFinded = false;

            byte checkSlots = getCheckbox(this.name, "Only Hotbar") ? (byte) 9 : (byte) 36;

            for (int i = 0; i < checkSlots; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                    ItemBlock itemBlock = (ItemBlock) mc.player.inventory.getStackInSlot(i).getItem();
                    if (itemBlock.getBlock() instanceof BlockConcretePowder) {
                        if (getMode(this.name, "ConcreteB Mode").equals("First"))
                            if (concreteFinded) continue;
                        concreteSlot = i;
                        concreteFinded = true;
                    } else if (itemBlock.getBlock().fullBlock) {
                        if (getMode(this.name, "SafeB Mode").equals("First"))
                            if (safeFinded) continue;
                        if (getCheckbox(this.name, "Obsidian SafeB Only"))
                            if (itemBlock.getBlock() != Blocks.OBSIDIAN) continue;
                        safeBlockSlot = i;
                        safeFinded = true;
                    }
                }
            }

            if (concreteSlot == -1) {
                return;
            }
            if (safeBlockSlot == -1) {
                if (getCheckbox(this.name, "Concrete In SafeB"))
                    safeBlockSlot = concreteSlot;
                else
                    return;
            }


            ArrayList<Vec3d> safeVectors;

            switch (AimBotUtils.getRoundedToCornersEntityRotationOnString(AimBotUtils.rotations(player)[0])) {
                case "X-":
                    safeVectors = new ArrayList<>(Arrays.asList(
                            new Vec3d(-1,0,0),
                            new Vec3d(-1,1,0),
                            new Vec3d(-1,2,0)));
                    break;
                case "Z-":
                    safeVectors = new ArrayList<>(Arrays.asList(
                            new Vec3d(0,0,-1),
                            new Vec3d(0,1,-1),
                            new Vec3d(0,2,-1)
                    ));
                    break;
                case "X+":
                    safeVectors = new ArrayList<>(Arrays.asList(
                            new Vec3d(1,0,0),
                            new Vec3d(1,1,0),
                            new Vec3d(1,2,0)
                    ));
                    break;
                case "Z+":
                default:
                    safeVectors = new ArrayList<>(Arrays.asList(
                            new Vec3d(0,0,1),
                            new Vec3d(0,1,1),
                            new Vec3d(0,2,1)
                    ));
                    break;
            }

            int finalAuxiliaryBlockSlot = safeBlockSlot;
            int finalConcreteSlot = concreteSlot;
            ThreadManager.startNewThread(thread -> {
                int swapDelay = (int) getSlider(this.name, "Swap Delay(MS)");
                placing = true;
                try {
                    int freeSlot = -1;

                    for (int i = 0; i < 9; i++) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.AIR) {
                            freeSlot = i;
                            break;
                        }
                    }
                    if (freeSlot == -1)
                        freeSlot = mc.player.inventory.currentItem;

                    for (int i = 0; i < 2; i++) {
                        if (finalAuxiliaryBlockSlot < 9) {
                            InventoryUtils.swapItem(finalAuxiliaryBlockSlot);
                        } else {
                            InventoryUtils.swapItemOnInventory(freeSlot, finalAuxiliaryBlockSlot);
                            mc.player.inventory.currentItem = freeSlot;
                            mc.playerController.updateController();
                        }
                        try {
                            thread.sleep(swapDelay);
                        } catch (Exception ignored) {}

                        BuildThread3D buildThread3D = new BuildThread3D();
                        buildThread3D.set3DSchematic((int) getSlider("AutoConcrete", "Place Delay"), safeVectors, playerPos);

                        buildThread3D.start();

                        thread.sleep(5);

                        while (BuildManager.isBuilding) {
                            thread.sleep(50);
                        }
                    }

                    for (int i = 0; i < (int) getSlider("AutoConcrete", "Concretes"); i++) {
                        if (finalConcreteSlot < 9) {
                            InventoryUtils.swapItem(finalConcreteSlot);
                        } else {
                            InventoryUtils.swapItemOnInventory(freeSlot, finalConcreteSlot);
                            mc.player.inventory.currentItem = freeSlot;
                            mc.playerController.updateController();
                        }
                        try {
                            thread.sleep(swapDelay);
                        } catch (Exception ignored) {}

                        BuildThread3D buildThread3D = new BuildThread3D();
                        buildThread3D.set3DSchematic((int) getSlider("AutoConcrete", "Place Delay"), new ArrayList<>(Arrays.asList(new Vec3d(0, 2, 0))), playerPos);

                        buildThread3D.start();

                        thread.sleep(5);

                        while (BuildManager.isBuilding) {
                            thread.sleep(50);
                        }
                    }
                } catch (Exception ignored) {}
                concretePositions.add(playerPos);
                placing = false;
            });
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        concretePositions = new HashSet<>();
    }
}

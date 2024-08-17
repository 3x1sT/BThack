package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.Build.BuildThread2D;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Build.BuildThread3D;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

//TODO : optimize
public class AutoBuilder extends Module {

    ArrayList<Vec2f> penisSchematic = new ArrayList<>(Arrays.asList(
            new Vec2f(0,0),
            new Vec2f(-1, 0),
            new Vec2f(1, 0),
            new Vec2f(0, 1),
            new Vec2f(0, 2)
    ));

    ArrayList<Vec2f> swastikaSchematic = new ArrayList<>(Arrays.asList(
            new Vec2f(-2, 0),
            new Vec2f(-1, 0),
            new Vec2f(0, 0),
            new Vec2f(0, 1),
            new Vec2f(0, 2),
            new Vec2f(1, 2),
            new Vec2f(2, 2),
            new Vec2f(2, 1),
            new Vec2f(2, 0),
            new Vec2f(-1, 2),
            new Vec2f(-2, 2),
            new Vec2f(-2, 3),
            new Vec2f(-2, 4),
            new Vec2f(0, 3),
            new Vec2f(0, 4),
            new Vec2f(1, 4),
            new Vec2f(2, 4)
    ));

    //TODO : rewrite
    ArrayList<Vec3d> platformSchematic = new ArrayList<>(Arrays.asList(
            new Vec3d(-1, 0, 0),
            new Vec3d(-1,0,1),
            new Vec3d(0,0, 1),
            new Vec3d(1,0,1),
            new Vec3d(1,0,0),
            new Vec3d(1,0,-1),
            new Vec3d(0,0,-1),
            new Vec3d(-1,0,-1),
            new Vec3d(-2,0,-1),
            new Vec3d(-2,0,0),
            new Vec3d(-2,0,1),
            new Vec3d(-1,0, 2),
            new Vec3d(0,0,2),
            new Vec3d(1,0,2),
            new Vec3d(2,0,1),
            new Vec3d(2,0,0),
            new Vec3d(2,0,-1),
            new Vec3d(1,0,-2),
            new Vec3d(0,0,-2),
            new Vec3d(-1,0,-2)
    ));

    ArrayList<Vec2f> wallSchematic = new ArrayList<>(Arrays.asList(
            //First layer
            new Vec2f(0,0),
            new Vec2f(-1,0),
            new Vec2f(-2,0),
            new Vec2f(1,0),
            new Vec2f(2,0),

            //Second layer
            new Vec2f(-2,1),
            new Vec2f(-1,1),
            new Vec2f(0,1),
            new Vec2f(1,1),
            new Vec2f(2,1),

            //Third layer
            new Vec2f(-2,2),
            new Vec2f(-1,2),
            new Vec2f(0,2),
            new Vec2f(1,2),
            new Vec2f(2,2)
    ));

    public AutoBuilder() {
        super("AutoBuilder",
                "Automatically builds the selected building.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        ArrayList<String> buildings = new ArrayList<>();
        buildings.add("Penis");
        buildings.add("Swastika");
        buildings.add("Platform");
        buildings.add("Wall");

        addMode("Mode", this, buildings);

        addSlider("Ticks delay", this, 1, 0, 5, true);

        addCheckbox("Only Obsidian", this, false);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        if (BuildManager.isBuilding) {
            sendError(ChatFormatting.YELLOW + "You're already building something.");
            toggle();
            return;
        }

        if (!mc.player.collidedVertically) {
            sendError(ChatFormatting.YELLOW + "You're not standing on the ground!");
            setToggled(false);
            return;
        }

        boolean isPlatform = getMode(this.name, "Mode").equals("Platform");

        int direction = AimBotUtils.getRoundedToStraightEntityRotation(mc.player);

        BlockPos blockPos;

        float playerX = (float) Math.floor(mc.player.posX) + 0.5f;
        float playerY = (float) Math.floor(mc.player.posY) + 0.5f;
        float playerZ = (float) Math.floor(mc.player.posZ) + 0.5f;

        if (!isPlatform) {
            switch (direction) {
                case 90:
                    blockPos = new BlockPos(new Vec3d(playerX - 2, mc.player.posY - 0.5, playerZ));
                    break;
                case 180:
                    blockPos = new BlockPos(new Vec3d(playerX, mc.player.posY - 0.5, playerZ - 2));
                    break;
                case 270:
                    blockPos = new BlockPos(new Vec3d(playerX + 2, mc.player.posY - 0.5, playerZ));
                    break;
                case 0:
                default:
                    blockPos = new BlockPos(new Vec3d(playerX, mc.player.posY - 0.5, playerZ + 2));
                    break;
            }
            if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
                blockPos = new BlockPos(new Vec3d(playerX - 2, mc.player.posY - 0.5, playerZ));
                direction = 90;
                if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    blockPos = new BlockPos(new Vec3d(playerX + 2, mc.player.posY - 0.5, playerZ));
                    direction = 270;
                    if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        blockPos = new BlockPos(new Vec3d(playerX, mc.player.posY - 0.5, playerZ - 2));
                        direction = 180;
                        if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
                            blockPos = new BlockPos(new Vec3d(playerX, mc.player.posY - 0.5, playerZ + 2));
                            direction = 0;
                            if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
                                sendError(ChatFormatting.YELLOW + "No blocks were found near you.");
                                setToggled(false);
                                return;
                            }
                        }
                    }
                }
            }
        } else {
            blockPos = new BlockPos(new Vec3d(playerX, mc.player.posY - 0.5, playerZ));
        }

        ArrayList<Vec2f> buildSchematic2D = new ArrayList<>();
        ArrayList<Vec3d> buildSchematic3D = new ArrayList<>();


        switch (getMode(this.name, "Mode")) {
            case "Penis":
                buildSchematic2D = penisSchematic;
                break;
            case "Swastika":
                buildSchematic2D = swastikaSchematic;
                break;
            case "Platform":
                buildSchematic3D = platformSchematic;
                break;
            case "Wall":
                buildSchematic2D = wallSchematic;
                break;
            default:
                sendError(ChatFormatting.YELLOW + "Error in selecting the right schematic.");
                toggle();
                return;
        }

        boolean isBlocksFinded = false;

        if (!(mc.player.itemStackMainHand.getItem() instanceof ItemBlock)) {
            for (int needSlot = 0; needSlot < 36; needSlot++) {
                Item item = mc.player.inventory.getStackInSlot(needSlot).getItem();
                if (item instanceof ItemBlock) {
                    ItemBlock itemBlock = (ItemBlock) item;
                    if (getCheckbox(this.name, "Only Obsidian")) {
                        if (itemBlock.getBlock() != Blocks.OBSIDIAN)
                            continue;
                    }

                    if (needSlot < 9) {
                        mc.player.inventory.currentItem = needSlot;
                        mc.playerController.updateController();

                        isBlocksFinded = true;
                    } else {
                        mc.playerController.updateController();
                        mc.playerController.windowClick(0, needSlot, mc.player.inventory.currentItem, ClickType.SWAP, mc.player);
                        mc.playerController.updateController();

                        isBlocksFinded = true;
                    }
                }
            }

            if (!isBlocksFinded) {
                sendError("No matching blocks were found in the inventory.");
                toggle();
                return;
            }
        }

        BuildThread2D thread2D = new BuildThread2D();
        BuildThread3D thread3D = new BuildThread3D();

        if (isPlatform) {
            thread3D.set3DSchematic((int) getSlider(this.name, "Ticks delay"), buildSchematic3D, blockPos);
            thread3D.start();
        } else {
            thread2D.set2DSchematic((int) getSlider(this.name, "Ticks delay"), buildSchematic2D, new BlockPos(blockPos.x, playerY, blockPos.z), direction);
            thread2D.start();
        }

        toggle();
    }






    private void sendError(String cause) {
        ChatUtils.sendMessage(cause, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);
    }
}

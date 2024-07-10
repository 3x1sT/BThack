package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BTbot.api.Utils.Motion.AlignWithXZ;
import com.bt.BTbot.api.Utils.Motion.WhereToAlign;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.Build.BuildThread3D;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class Surround extends Module {

    ArrayList<Vec3d> surroundVector = new ArrayList<>(Arrays.asList(
            new Vec3d(1,0,0),
            new Vec3d(-1,0,0),
            new Vec3d(0,0,1),
            new Vec3d(0,0,-1),

            new Vec3d(1,1,0),
            new Vec3d(-1,1,0),
            new Vec3d(0,1,1),
            new Vec3d(0,1,-1)
    ));

    public Surround() {
        super("Surround",
                "Surrounds you with obsidian to take less damage.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        addCheckbox("Check Obsidian", this, true);
    }


    private boolean isBlocksFinded = false;

    @Override
    public void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        if (!mc.player.collidedVertically) {
            sendError(ChatFormatting.YELLOW + "You're not standing on the ground!");
            toggle();
            return;
        }

        if (BuildManager.isBuilding) {
            sendError(ChatFormatting.YELLOW + "You're already building something.");
            toggle();
            return;
        }

        BlockPos blockPos = new BlockPos(new Vec3d(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ));

        if (getCheckbox(this.name, "Check Obsidian")) {
            if (mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                sendError(ChatFormatting.YELLOW + "There's no obsidian underneath you!");
                toggle();
                return;
            }
        }

        WhereToAlign whereToAlign = new WhereToAlign();
        AlignWithXZ alignWithXZ = new AlignWithXZ(whereToAlign);

        alignWithXZ.alignWithXZ();



        isBlocksFinded = false;

        if (!(mc.player.itemStackMainHand.getItem() instanceof ItemBlock)) {
            findObsidian();

            if (!isBlocksFinded)
                return;
        } else {
            ItemBlock block = (ItemBlock) mc.player.itemStackMainHand.getItem();

            if (block.getBlock() != Blocks.OBSIDIAN) {
                findObsidian();

                if (!isBlocksFinded)
                    return;
            }
        }

        BuildThread3D thread = new BuildThread3D();

        thread.set3DSchematic(1, surroundVector, blockPos);
        thread.start();
        toggle();
    }




    private void sendError(String cause) {
        ChatUtils.sendMessage(cause, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);
    }

    private void findObsidian() {
        for (int needSlot = 0; needSlot < 36; needSlot++) {
            Item item = mc.player.inventory.getStackInSlot(needSlot).getItem();
            if (item instanceof ItemBlock) {
                ItemBlock block = (ItemBlock) item;

                if (block.getBlock() == Blocks.OBSIDIAN) {

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
        }

        if (!isBlocksFinded) {
            sendError("No matching blocks were found in the inventory.");
            toggle();
        }
    }
}

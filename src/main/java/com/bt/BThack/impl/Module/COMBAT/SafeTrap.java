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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class SafeTrap extends Module {

    ArrayList<Vec3d> trapVector = new ArrayList<>(Arrays.asList(
            new Vec3d(1,0,0),
            new Vec3d(-1,0,0),
            new Vec3d(0,0,1),
            new Vec3d(0,0,-1),

            new Vec3d(1,1,0),
            new Vec3d(-1,1,0),
            new Vec3d(0,1,1),
            new Vec3d(0,1,-1),

            new Vec3d(1,2,0),
            new Vec3d(-1,2,0),
            new Vec3d(0,2,1),
            new Vec3d(0,2,-1),

            new Vec3d(1,3,0),
            new Vec3d(0,3,0)
    ));

    public SafeTrap() {
        super("SafeTrap",
                "Builds you into a trap if you're low on xp.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        addSlider("Min Health", this, 7, 1, 15, false);
        addCheckbox("Check Obsidian", this, false);
    }

    private boolean isBlockFinded;
    private boolean startBuild = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        if (mc.player.getHealth() <= getSlider(this.name, "Min Health") && mc.player.collidedVertically && !BuildManager.isBuilding) {
            if (startBuild) {
                ChatUtils.sendMessage(getChatName() + "The trap is built, the disable ...");
                toggle();
                startBuild = false;
                return;
            }

            BlockPos blockPos = new BlockPos(new Vec3d(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ));

            if (getCheckbox(this.name, "Check Obsidian")) {
                if (mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                    sendError(ChatFormatting.YELLOW + getChatName() + "There's no obsidian underneath you!");
                    return;
                }
            }

            WhereToAlign whereToAlign = new WhereToAlign();
            AlignWithXZ alignWithXZ = new AlignWithXZ(whereToAlign);

            alignWithXZ.alignWithXZ();

            isBlockFinded = false;

            findObsidian();

            if (!isBlockFinded) {
                return;
            }

            BuildThread3D thread = new BuildThread3D();

            thread.set3DSchematic(1, trapVector, blockPos);
            thread.start();
            startBuild = true;
        }
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
                    isBlockFinded = true;
                    break;
                }
            }
        }

        if (!isBlockFinded) {
            sendError(ChatFormatting.YELLOW + getChatName() + "No matching blocks were found in the inventory.");
        }
    }
}

package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks;

import com.bt.BTbot.api.Utils.Motion.AlignWithXZ;
import com.bt.BTbot.api.Utils.Motion.WhereToAlign;
import com.bt.BTbot.api.Utils.Rotate.RotateMath;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Destroy.DestroyManager;
import com.bt.BThack.api.Utils.Destroy.SimpleDestroyThread;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTask;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;

public class MoveTask extends ActionBotTask {
    private final Type type;
    private final double needX;
    private final double needZ;
    private final boolean scaffold;


    public MoveTask(double needX, double needZ, boolean scaffold, Type type) {
        super("Move");
        this.mode = "Move";

        this.needX = needX;
        this.needZ = needZ;
        this.scaffold = scaffold;
        this.type = type;

        this.taskDescription = new ArrayList<>(Arrays.asList(
           "When the task is activated, the player starts walking to the specified coordinates.",
           "Coordinates should be entered by the ratio of the player's coordinates.",
           "I.e., if you need to write 5 at x coordinates, if you want the",
           "the player has moved 5 blocks along the x-coordinate."
        ));
    }
    public double maxX;
    public double minX;
    public double maxZ;
    public double minZ;
    protected boolean cancel;

    @Override
    public void play() {
        WhereToAlign whereToAlign = new WhereToAlign();
        AlignWithXZ alignWithXZ = new AlignWithXZ(whereToAlign);
        alignWithXZ.alignWithXZ();
        do {
            sleepThread(50);
        } while (alignWithXZ.isMoving());


        double tempX = mc.player.posX + needX;
        double tempZ = mc.player.posZ + needZ;

        boolean scaffoldActivated = true;

        if (scaffold) {
            scaffoldActivated = Client.getModuleByName("Scaffold").isEnabled();
            Client.getModuleByName("Scaffold").setToggled(true);
        }


        maxX = tempX + 0.15;
        minX = tempX - 0.15;
        maxZ = tempZ + 0.15;
        minZ = tempZ - 0.15;

        while (mc.player.posX != tempX || mc.player.posZ != tempZ) {

            mc.player.rotationYaw = RotateMath.rotations(tempX, mc.player.posY, tempZ)[0];
            mc.gameSettings.keyBindForward.pressed = !DestroyManager.isDestroying || type != Type.Through_Obstacles;

            if (scaffold) {
                if (!Client.getModuleByName("Scaffold").isEnabled()) {
                    Client.getModuleByName("Scaffold").setToggled(true);
                }
            }

            if (mc.player.collidedHorizontally) {
                switch (type) {
                    case Default:
                        ChatUtils.sendMessage("[ActionBot: MoveTask] " + ChatFormatting.YELLOW + "The player ran into an obstacle. Skipping a task.");
                        mc.gameSettings.keyBindForward.pressed = false;
                        disableScaffold(scaffoldActivated);
                        return;
                    case AutoJump:
                        tryJump();
                        break;
                    case Through_Obstacles:
                        mc.gameSettings.keyBindForward.pressed = false;
                        if (!DestroyManager.isDestroying) {
                            double x = mc.player.posX + AimBotUtils.getCordFactorFromDirection(mc.player)[0];
                            double z = mc.player.posZ + AimBotUtils.getCordFactorFromDirection(mc.player)[1];
                            double y = mc.player.posY + 0.5;
                            BlockPos blockPos = new BlockPos(x, y, z);
                            if (BuildManager.ignoreBlocks.contains(mc.world.getBlockState(blockPos).getBlock())) {
                                blockPos = new BlockPos(blockPos.x, blockPos.y + 1, blockPos.z);
                            }
                            SimpleDestroyThread destroyThread = new SimpleDestroyThread(blockPos);
                            destroyThread.start();
                        }
                        break;
                }
            }

            if (!toFarX() && !toFarZ()) {
                mc.gameSettings.keyBindForward.pressed = false;
                cancel = false;
                disableScaffold(scaffoldActivated);
                return;
            }

            if (cancel) {
                cancel = false;
                mc.gameSettings.keyBindForward.pressed = false;
                disableScaffold(scaffoldActivated);
                return;
            }
        }
        cancel = false;
        disableScaffold(scaffoldActivated);
    }
    private void disableScaffold(boolean scaffoldEnabled) {
        if (scaffold) {
            if (!scaffoldEnabled) {
                Client.getModuleByName("Scaffold").setToggled(false);
            }
        }
    }

    private boolean toFarX() {
        return mc.player.posX > maxX || mc.player.posX < minX;
    }

    private boolean toFarZ() {
        return mc.player.posZ > maxZ || mc.player.posZ < minZ;
    }

    private void tryJump() {
        mc.gameSettings.keyBindForward.pressed = false;
        double oldPosY = mc.player.posY;
        mc.player.jump();
        mc.gameSettings.keyBindForward.pressed = true;

        sleepThread(600);

        mc.gameSettings.keyBindForward.pressed = false;
        if (mc.player.posY < oldPosY + 0.4) {
            mc.player.jump();
            mc.gameSettings.keyBindForward.pressed = true;

            sleepThread(600);

            mc.gameSettings.keyBindForward.pressed = false;
        }
        if (mc.player.posY < oldPosY + 0.4) {
            mc.player.jump();
            mc.gameSettings.keyBindForward.pressed = true;

            sleepThread(600);

            mc.gameSettings.keyBindForward.pressed = false;
        }
        if (mc.player.posY < oldPosY + 0.4) {
            cancel = true;
        }
    }





    public double getNeedX() {
        return this.needX;
    }

    public double getNeedZ() {
        return this.needZ;
    }

    public boolean isScaffold() {
        return this.scaffold;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        Default,
        AutoJump,
        Through_Obstacles
    }
}

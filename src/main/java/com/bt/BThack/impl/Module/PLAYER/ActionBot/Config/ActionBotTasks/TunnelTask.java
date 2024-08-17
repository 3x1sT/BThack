package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks;

import com.bt.BTbot.api.Utils.Motion.AlignWithXZ;
import com.bt.BTbot.api.Utils.Motion.WhereToAlign;
import com.bt.BTbot.api.Utils.Rotate.RotateMath;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.Build.BuildThread3D;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Destroy.DestroyManager;
import com.bt.BThack.api.Utils.Destroy.SimpleDestroyThread;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTask;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;


// TODO : Add more checks
public class TunnelTask extends ActionBotTask {


    public TunnelTask(Direction direction, double length) {
        super("Make Tunnel");
        this.mode = "Tunnel";

        this.direction = direction;
        this.length = length;

        this.needX = this.length * this.direction.getX();
        this.needZ = this.length * this.direction.getZ();

        this.taskDescription = new ArrayList<>(Arrays.asList(
                "The more advanced version of MoveTask is equipped with special checks necessary for more safe tunnel digging.",
                "Unlike its ancestor, the direction and final length of the tunnel are used instead of coordinates. "
        ));
    }

    private final double length;
    private final Direction direction;

    private final double needX;
    private final double needZ;

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

        boolean scaffoldActivated;

        scaffoldActivated = Client.getModuleByName("Scaffold").isEnabled();
        Client.getModuleByName("Scaffold").setToggled(true);


        maxX = tempX + 0.15;
        minX = tempX - 0.15;
        maxZ = tempZ + 0.15;
        minZ = tempZ - 0.15;


        while (mc.player.posX != tempX || mc.player.posZ != tempZ) {

            mc.player.rotationYaw = RotateMath.rotations(tempX, mc.player.posY, tempZ)[0];
            mc.gameSettings.keyBindForward.pressed = !DestroyManager.isDestroying;

            if (!Client.getModuleByName("Scaffold").isEnabled()) {
                Client.getModuleByName("Scaffold").setToggled(true);
            }

            BlockPos blockPos;
            BlockPos blockPos2;
            double x;
            double y = mc.player.posY + 0.5;
            double z;

            if (checkLava()) {
                ChatUtils.sendMessage("Lava Detected!");
                mc.gameSettings.keyBindForward.pressed = false;
                emergencyTrap();
                cancel = false;
                disableScaffold(scaffoldActivated);
                return;
            }

            if (!mc.player.collidedHorizontally) {
                x = mc.player.posX + (AimBotUtils.getCordFactorFromDirection(mc.player)[0] * 2);
                z = mc.player.posZ + (AimBotUtils.getCordFactorFromDirection(mc.player)[1] * 2);
            } else {
                x = mc.player.posX + (AimBotUtils.getCordFactorFromDirection(mc.player)[0]);
                z = mc.player.posZ + (AimBotUtils.getCordFactorFromDirection(mc.player)[1]);
            }

            blockPos = new BlockPos(x, y, z);
            blockPos2 = new BlockPos(blockPos.x, blockPos.y + 1, blockPos.z);

            if (!BuildManager.ignoreBlocks.contains(mc.world.getBlockState(blockPos).getBlock()) || !BuildManager.ignoreBlocks.contains(mc.world.getBlockState(blockPos2).getBlock())) {
                mc.gameSettings.keyBindForward.pressed = false;
                if (!DestroyManager.isDestroying) {
                    if (BuildManager.ignoreBlocks.contains(mc.world.getBlockState(blockPos2).getBlock())) {
                        blockPos2 = new BlockPos(blockPos.x, blockPos.y, blockPos.z);
                    }
                    SimpleDestroyThread destroyThread = new SimpleDestroyThread(blockPos2);
                    destroyThread.start();
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
        if (!scaffoldEnabled) {
            Client.getModuleByName("Scaffold").setToggled(false);
        }
    }

    private boolean toFarX() {
        return mc.player.posX > maxX || mc.player.posX < minX;
    }

    private boolean toFarZ() {
        return mc.player.posZ > maxZ || mc.player.posZ < minZ;
    }

    private boolean checkLava() {
        double x = mc.player.posX + (AimBotUtils.getCordFactorFromDirection(mc.player)[0] * 2);
        double y = mc.player.posY + 0.5;
        double z = mc.player.posZ + (AimBotUtils.getCordFactorFromDirection(mc.player)[1] * 2);

        ArrayList<BlockPos> blockPosData = new ArrayList<>(Arrays.asList(
                new BlockPos(x, y, z),
                new BlockPos(x - 1, y, z),
                new BlockPos(x + 1, y, z),
                new BlockPos(x, y, z + 1),
                new BlockPos(x, y, z - 1)
        ));

        for (BlockPos pos : blockPosData) {
            if (lavaData.contains(mc.world.getBlockState(pos).getBlock())) {
                return true;
            } else if (lavaData.contains(mc.world.getBlockState(new BlockPos(pos.x, pos.y - 1, pos.z)).getBlock())) {
                return true;
            } else if (lavaData.contains(mc.world.getBlockState(new BlockPos(pos.x, pos.y + 1, pos.z)).getBlock())) {
                return true;
            } else if (lavaData.contains(mc.world.getBlockState(new BlockPos(pos.x, pos.y + 2, pos.z)).getBlock())) {
                return true;
            }
        }
        return false;
    }

    private void emergencyTrap() {
        ArrayList<Vec3d> trapSchematic = new ArrayList<>(Arrays.asList(
                new Vec3d(1,1,0),
                new Vec3d(-1,1,0),
                new Vec3d(0,1,1),
                new Vec3d(0,1,-1),
                new Vec3d(1,2,0),
                new Vec3d(-1,2,0),
                new Vec3d(0,2,1),
                new Vec3d(0,2,-1),
                new Vec3d(0,3,0)
        ));

        WhereToAlign whereToAlign = new WhereToAlign();
        AlignWithXZ alignWithXZ = new AlignWithXZ(whereToAlign);

        alignWithXZ.alignWithXZ();

        for (int needSlot = 0; needSlot < 36; needSlot++) {
            Item item = mc.player.inventory.getStackInSlot(needSlot).getItem();
            if (item instanceof ItemBlock) {
                if (needSlot < 9) {
                    mc.player.inventory.currentItem = needSlot;

                } else {
                    mc.playerController.updateController();
                    mc.playerController.windowClick(0, needSlot, mc.player.inventory.currentItem, ClickType.SWAP, mc.player);

                }
                mc.playerController.updateController();
                break;
            }
        }

        BlockPos startPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ);

        BuildThread3D buildThread3D = new BuildThread3D();
        buildThread3D.set3DSchematic(1, trapSchematic, startPos);
        buildThread3D.start();
    }






    public Direction getDirection() {
        return this.direction;
    }

    public double getLength() {
        return this.length;
    }


    public enum Direction {
        X_PLUS(1,0),
        X_MINUS(-1,0),
        Z_PLUS(0,1),
        Z_MINUS(0,-1);


        private final int x;
        private final int z;

        Direction(int x, int z) {
            this.x = x;
            this.z = z;
        }

        public int getX() {
            return this.x;
        }

        public int getZ() {
            return this.z;
        }
    }


    private Set<Block> lavaData = Sets.newHashSet(
            Blocks.LAVA,
            Blocks.FLOWING_LAVA
    );
}

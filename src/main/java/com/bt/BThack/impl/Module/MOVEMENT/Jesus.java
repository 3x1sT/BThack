package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Objects;

public class Jesus extends Module {
    public Jesus() {
        super("Jesus",
                "Allows the player to walk on water.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("Dolphin");
        options.add("Matrix");

        addMode("Mode", this, options, "Mode");
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        String Mode = getMode(this.name, "Mode");

        BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ);
        Block block = mc.world.getBlockState(blockPos).getBlock();

        if (mc.player != null) {
            if (Objects.equals(Mode, "Matrix")) {
                if (Block.getIdFromBlock(block) == 9) {
                    if (!mc.player.onGround) {
                        speed(2.5);

                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.0000001, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                            mc.player.fallDistance = 0.0f;
                            mc.player.motionX = 0;
                            mc.player.motionY = 0.06f;
                            mc.player.jumpMovementFactor = 0.01f;
                            mc.player.motionZ = 0;
                        }
                    }
                }
            } else {
                if (mc.player.isInWater() || mc.player.isInLava()) {
                    if (!mc.player.collidedHorizontally || !mc.player.isSneaking()) {
                        mc.player.motionY = 0.05;
                        mc.player.onGround = true;
                    }
                }
            }
        }
    }
    public static void speed(double speed) {
        Minecraft mc = Minecraft.getMinecraft();

        double forvard = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;

        if (forvard == 0.0 && strafe == 0) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        } else {
            if (forvard != 0) {
                if (strafe < 0) {
                    yaw += (float) (forvard > 0 ? -45 : 45);
                } else if (strafe < 0) {
                    yaw += (float) (forvard > 0 ? 45 : -45);
                }

                strafe = 0;
                if (forvard > 0) {
                    forvard = 1;
                } else if (forvard < 0) {
                    forvard = -1;
                }

                mc.player.motionX = forvard * speed * Math.cos(Math.toRadians(yaw + 90)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90));
                mc.player.motionZ = forvard * speed * Math.sin(Math.toRadians(yaw + 90)) + strafe * speed * Math.cos(Math.toRadians(yaw + 90));
            }
        }
    }
}
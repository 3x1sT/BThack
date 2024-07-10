package com.bt.BThack.impl.Module.MOVEMENT.NinjaBridge;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.ItemsUtil;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;

public class NinjaBridgeThread extends Thread implements Mc {
    private static final String moduleName = "NinjaBridge";

    protected static boolean close = false;

    public void run() {
        //float x = (float)(mc.player.posX - ((int) mc.player.posX));
        //float z = (float)(mc.player.posZ - ((int) mc.player.posZ));
        //if ((x < 0.1f || x > 0.3f) && (z < 0.1f || z > 0.3f)) {
        //    ChatUtils.sendMessage(ChatFormatting.YELLOW + "Stand on the edge of the block where you want to start building.");
        //    Client.getModuleByName(moduleName).setToggled(false);
        //    stopMotion();
        //    stop();
        //}

        int yaw = AimBotUtils.getRoundedToCornersEntityRotation(mc.player);
        float pitch = 77.7f;

        int itemBlock = ItemsUtil.getBlock();

        if (itemBlock == -1) {
            close("No blocks found in your hotbar.");
        }

        while (Client.getModuleByName(moduleName).isEnabled()) {

            ///Получение настроек с модуля///
            long actionDelay = (long) Module.getSlider(moduleName, "Action delay");
            long placeTime = (long) Module.getSlider(moduleName, "Place block time");
            long placeFactor = (long) Module.getSlider(moduleName, "Place block factor");
            float extraAirDistance = (float) Module.getSlider(moduleName, "Extra air check");
            ///

            BlockPos block = getBlockPos(yaw, extraAirDistance);

            IBlockState blockState = mc.player.world.getBlockState(block);

            if (mc.player.collidedHorizontally) {
                close("You have encountered an obstacle.");
            }

            mc.player.inventory.currentItem = itemBlock;

            mc.player.rotationYaw = yaw;
            mc.player.rotationPitch = pitch;

            while (blockState.getBlock() != Blocks.AIR && Client.getModuleByName(moduleName).isEnabled()) {
                mc.gameSettings.keyBindBack.pressed = true;
                mc.gameSettings.keyBindRight.pressed = true;

                //Задержка между действиями
                if (actionDelay != 0) {
                    try {
                        sleep(actionDelay);
                    } catch (InterruptedException ignored) {
                    }
                }
                //

                checkBlocks();

                block = getBlockPos(yaw, extraAirDistance);
                blockState = mc.player.world.getBlockState(block);
            }
            while (blockState.getBlock() == Blocks.AIR && Client.getModuleByName(moduleName).isEnabled()) {
                mc.gameSettings.keyBindSneak.pressed = true;
                placeBlock(placeTime, placeFactor);

                //Задержка между действиями
                if (actionDelay != 0) {
                    try {
                        sleep(actionDelay);
                    } catch (InterruptedException ignored) {}
                }
                //

                checkBlocks();

                block = getBlockPos(yaw, extraAirDistance);
                blockState = mc.player.world.getBlockState(block);
            }
            mc.gameSettings.keyBindSneak.pressed = false;

            //Проверяет нужно ли закрыть поток, если нужно то закрывает его, выполнив перед этим нужную логику
            if (close) {
                stopMotion();
                stop();
            }
        }
        stopMotion();
    }

    //Спамит кейбиндом на использование предмета, что-бы поставить блок
    private static void placeBlock(long time, long factor) {
        long a = time;
        while (a > 0) {
            mc.gameSettings.keyBindUseItem.pressed = true;
            try {
                sleep(factor);
            } catch (InterruptedException ignored) {}
            mc.gameSettings.keyBindUseItem.pressed = false;
            a -= factor;
        }
    }

    //Останавливает движение игрока(нужно при выключении модуля)
    private static void stopMotion() {
        mc.gameSettings.keyBindBack.pressed = false;
        mc.gameSettings.keyBindRight.pressed = false;
        mc.gameSettings.keyBindUseItem.pressed = false;
    }

    //Получает BlockPos под игроком + доп.расстояние
    private static BlockPos getBlockPos(int yaw, float extraRange) {
        BlockPos block = null;
        switch (yaw) {
            case -135:
                block = new BlockPos(mc.player.posX, mc.player.posY - 0.5F, mc.player.posZ + extraRange);
                break;
            case -45:
                block = new BlockPos(mc.player.posX - extraRange, mc.player.posY - extraRange, mc.player.posZ);
                break;
            case 45:
                block = new BlockPos(mc.player.posX, mc.player.posY - 0.5F, mc.player.posZ - extraRange);
                break;
            case 135:
                block = new BlockPos(mc.player.posX + extraRange, mc.player.posY - 0.5F, mc.player.posZ);
        }

        return block;
    }

    private static void close(String message) {
        ChatUtils.sendMessage(ChatFormatting.YELLOW + message);
        Client.getModuleByName(moduleName).setToggled(false);
        close = true;
    }

    /*
    Проверяет есть ли в руке блоки, если нет то ищет их в хотбаре.
    Если не найдено ни одного блока то отключает модуль и выводит сообщение в чат.
     */
    private static void checkBlocks() {
        int itemBlock;
        if (!(mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() instanceof ItemBlock)) {
            itemBlock = ItemsUtil.getBlock();

            if (itemBlock == -1) {
                close("No blocks found in your hotbar.");
            } else {
                mc.player.inventory.currentItem = itemBlock;
            }
        }
    }
}

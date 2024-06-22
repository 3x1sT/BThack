package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoTool extends Module {
    private int oldSlot = -1;
    private boolean status;
    public AutoTool() {
        super("AutoTool",
                "Automatically takes the desired tool into your hand.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.objectMouseOver != null && mc.gameSettings.keyBindAttack.isPressed()) {
            int bestSlot = findBestSlot();

            if (bestSlot == -1) return;

            status = true;

            if (oldSlot == -1) oldSlot = mc.player.inventory.currentItem;

            mc.player.inventory.currentItem = findBestSlot();
        } else if (status) {
            mc.player.inventory.currentItem = oldSlot;
            reset();
        }
    }

    private void reset() {
        oldSlot = -1;
        status = false;
    }

    private int findBestSlot() {
        if (mc.objectMouseOver instanceof RayTraceResult) {
            RayTraceResult RayTraceResult = (RayTraceResult) mc.objectMouseOver;
            Block block = mc.world.getBlockState(RayTraceResult.getBlockPos()).getBlock();

            int bestSlot = -1;
            float bestSpeed = 1.0f;

            for (int slot = 0; slot < 9; slot++) {
                ItemStack stack = mc.player.inventory.getStackInSlot(slot);
                float speed = stack.getDestroySpeed(block.getDefaultState());

                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = slot;
                }
            }
            return bestSlot;
        }
        return -1;

    }

    @Override
    public void onDisable() {
        reset();
        super.onDisable();
    }
}

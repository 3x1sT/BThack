package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.MathUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.Events.RightClickBlockEvent;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class LastOpenChest extends Module {

    public LastOpenChest() {
        super("LastOpenChest",
                "Highlights the last opened chest",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Red", this, 255, 0, 255, true);
        addSlider("Green", this, 255, 0, 255, true);
        addSlider("Blue", this, 255, 0, 255, true);
    }

    BlockPos chestPos;
    boolean needRender = false;

    @SubscribeEvent
    public void onUseBlock(RightClickBlockEvent e) {
        if (isChest(e.blockPos)) {
            chestPos = e.blockPos;
            needRender = false;
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
        if (e.getGui() instanceof GuiChest) {
            if (chestPos != null) {
                if (MathUtils.getDistance(mc.player.getPositionVector(), new Vec3d(chestPos.x, chestPos.y, chestPos.z)) > 20) {
                    chestPos = null;
                    needRender = false;
                } else {
                    needRender = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        if (chestPos != null) {
            if (!isChest(chestPos))
                chestPos = null;
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        if (chestPos != null && needRender) {
            float red = (float) getSlider(this.name, "Red") / 255f;
            float green = (float) getSlider(this.name, "Green") / 255f;
            float blue = (float) getSlider(this.name, "Blue") / 255f;

            RenderUtils.drawBox(chestPos, red, green, blue, 0.6f, red, green, blue, 0.4f);
        }
    }

    private boolean isChest(BlockPos pos) {
        Block block = mc.world.getBlockState(pos).getBlock();
        return block == Blocks.CHEST || block == Blocks.ENDER_CHEST || block == Blocks.TRAPPED_CHEST;
    }
}

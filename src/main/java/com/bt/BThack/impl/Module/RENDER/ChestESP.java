package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ChestESP extends Module {
    public ChestESP() {
        super("ChestESP",
                "Highlighting containers.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addCheckbox("Tracers", this, false);
    }
    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        if (nullCheck()) return;

        for (Object c : mc.world.loadedTileEntityList) {
            if (c instanceof TileEntityChest) {
                RenderUtils.ChestESP(((TileEntityChest) c).getPos(), 1F, 0.39F, 0, 0.6F, 1f, 0.53f, 0, 0.4F);
                if (getCheckbox(this.name, "Tracers")) {
                    RenderUtils.trace(((TileEntityChest) c), 1F, 0.39F, 0, 1f);
                }
            }
            if (c instanceof TileEntityEnderChest) {
                RenderUtils.ChestESP(((TileEntityEnderChest) c).getPos(), 1, 0, 1, 0.6F, 1, 0, 1, 0.4F);
                if (getCheckbox(this.name, "Tracers")) {
                    RenderUtils.trace(((TileEntityEnderChest) c), 1, 0, 1, 1f);
                }
            }
            if (c instanceof TileEntityShulkerBox) {
                RenderUtils.ChestESP(((TileEntityShulkerBox) c).getPos(), 1, 0.45F, 1, 0.6F, 1, 0.3F, 1, 0.4F);
                if (getCheckbox(this.name, "Tracers")) {
                    RenderUtils.trace(((TileEntityShulkerBox) c), 1, 0.45F, 1, 1f);
                }
            }
        }
    }
}
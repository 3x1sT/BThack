package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BlockHighlight extends Module {

    public BlockHighlight() {
        super("BlockHighlight",
                "Highlights the block that you are looking at",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Outline Width", this, 2, 1, 5, true);
        addSlider("Render Red", this, 200, 0, 255, true);
        addSlider("Render Green", this, 200, 0, 255, true);
        addSlider("Render Blue", this, 200, 0, 255, true);
        addSlider("Render Alpha", this, 220, 0, 255, true);
        addSlider("Lines Alpha", this, 255, 0, 255, true);
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent e) {
        if (nullCheck()) return;

        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            GL11.glLineWidth((float) getSlider(this.name, "Outline Width"));

            Color color = new Color((int) getSlider(this.name, "Render Red"), (int) getSlider(this.name, "Render Green"), (int) getSlider(this.name, "Render Blue"), (int) getSlider(this.name, "Render Alpha"));

            RenderUtils.drawBox(mc.objectMouseOver.getBlockPos(), new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) getSlider(this.name, "Lines Alpha")), color);
        }
    }
}

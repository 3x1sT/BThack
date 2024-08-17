package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class Caipirinha extends Module {

    public Caipirinha() {
        super("Caipirinha",
                "Caipirinha! Caipirinha!",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Size", this, 7, 15, 5, true);
        addSlider("Speed", this, 1.1, 0.7, 1.35, false);
    }

    double currentCaipirinha = 0;
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        double caipirinha = currentCaipirinha;

        caipirinha += 0.15 * getSlider(this.name, "Speed");

        if (caipirinha > 6) {
            caipirinha = 0;
        }

        currentCaipirinha = caipirinha;
    }

    ArrayList<ResourceLocation> caipirinhi = new ArrayList<>(Arrays.asList(
            new ResourceLocation("bthack", "caipirinha/caipirinha1.png"),
            new ResourceLocation("bthack", "caipirinha/caipirinha2.png"),
            new ResourceLocation("bthack", "caipirinha/caipirinha3.png"),
            new ResourceLocation("bthack", "caipirinha/caipirinha4.png"),
            new ResourceLocation("bthack", "caipirinha/caipirinha5.png"),
            new ResourceLocation("bthack", "caipirinha/caipirinha6.png"),
            new ResourceLocation("bthack", "caipirinha/caipirinha6.png")
    ));

    @SubscribeEvent
    public void onGuiRender(RenderGameOverlayEvent e) {
        if (Objects.requireNonNull(e.getType()) == RenderGameOverlayEvent.ElementType.HOTBAR) {
            if (nullCheck()) return;

            ScaledResolution sc = e.getResolution();

            int size = sc.getScaledWidth() / (int) getSlider(this.name, "Size");

            GlStateManager.pushMatrix();

            mc.renderEngine.bindTexture(caipirinhi.get((int) currentCaipirinha));
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            RenderUtils.drawScaledCustomSizeModalRect((sc.getScaledWidth() / 4) - (size / 2), (sc.getScaledHeight() - size) + (size * 0.065), 0, 0, size, size, size, size, size, size);
            GlStateManager.popMatrix();
        }
    }
}

package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Objects;

public class CS_Crosshair extends Module {

    private float spread = 0;

    public CS_Crosshair() {
        super("CS_Crosshair",
                "Swaps the crosshairs for the crosshairs from Counter Strike.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Width", this, 4, 1, 50, false);
        addSlider("Height", this, 1, 1, 10, false);
        addSlider("Distance", this, 3, 2, 30, true);

        addCheckbox("Movable", this, true);
        addSlider("Movable", true, "Scatter Limit", this, 15, 6, 30, true);
        addSlider("Movable", true, "Scatter Speed", this, 0.15, 0.05, 0.3, false);

        addCheckbox("Left Rect", this, true);
        addCheckbox("Right Rect", this, true);
        addCheckbox("Up Rect", this, true);
        addCheckbox("Down Rect", this, true);
        addCheckbox("Center Rect", this, true);

        addSlider("Rainbow", false,"Red", this, 0, 0, 255, true);
        addSlider("Rainbow", false,"Green", this, 255, 0, 255, true);
        addSlider("Rainbow", false,"Blue", this, 0, 0, 255, true);

        addSlider("Rotate", this, 0, 0, 90, true);

        addCheckbox("Rainbow", this, false);
    }

    @SubscribeEvent
    public void onOverlay(RenderGameOverlayEvent e) {
        if (nullCheck()) return;
        if  (Objects.requireNonNull(e.getType()) == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            if (!getCheckbox(this.name, "Movable"))
                spread = 0;

            ScaledResolution sc = new ScaledResolution(mc);

            double width = getSlider(this.name, "Width");
            double height = getSlider(this.name, "Height");
            double distance = getSlider(this.name, "Distance");
            Color color = getCheckbox(this.name, "Rainbow") ? new Color(ColourUtils.rainbow(100)) : new Color((int)getSlider(this.name, "Red"), (int)getSlider(this.name, "Green"), (int)getSlider(this.name, "Blue"));

            GlStateManager.translate(sc.getScaledWidth() / 2f, sc.getScaledHeight() / 2f, 0);
            GlStateManager.rotate((float) getSlider(this.name, "Rotate"), 0, 0, 1);

            //center
            if (getCheckbox(this.name, "Center Rect"))
                Gui.drawRect((int)-height, (int)- height, (int)height, (int)height, color.hashCode());

            //up
            if (getCheckbox(this.name, "Up Rect"))
                Gui.drawRect((int)(0 -height), (int)(0 - height - distance - spread), (int)(0 + height), (int)(0 - height - distance - width - spread), color.hashCode());

            //left
            if (getCheckbox(this.name, "Left Rect"))
                Gui.drawRect((int)(0 - height - distance - spread), (int)(0 - height), (int)(0 - height - distance - width - spread), (int)(0 + height), color.hashCode());

            //down
            if (getCheckbox(this.name, "Down Rect"))
                Gui.drawRect((int)-height, (int)(0 + height + distance + spread), (int)( + height), (int)(0 + height + distance + width + spread), color.hashCode());

            //right
            if (getCheckbox(this.name, "Right Rect"))
                Gui.drawRect((int)(0 + height + distance + spread), (int)(0 - height), (int)(0 + height + distance + width + spread), (int)(0 + height), color.hashCode());

            GlStateManager.rotate((float) -getSlider(this.name, "Rotate"), 0, 0, 1);
            GlStateManager.translate(-(sc.getScaledWidth() / 2f), -(sc.getScaledHeight() / 2f), 0);

            spread -= (float) getSlider(this.name, "Scatter Speed");
            if (spread < 0)
                spread = 0;

            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onAction(PacketEvent.Send e) {
        if (nullCheck() || !getCheckbox(this.name, "Movable")) return;

        if (e.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) e.getPacket();

            if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK) {
                spread += 6;
                if (spread > getSlider(this.name, "Scatter Limit"))
                    spread = (int)getSlider(this.name, "Scatter Limit");
            }
        }
    }
}

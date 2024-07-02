package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class NoOverlay extends Module {
    public NoOverlay() {
        super("NoOverlay",
                "Disables overlay rendering.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addCheckbox("Boss Bar", this, true);
        addCheckbox("Hurt Camera", this, true);
    }

    private final RenderGameOverlayEvent.ElementType[] elementType = {RenderGameOverlayEvent.ElementType.PORTAL, RenderGameOverlayEvent.ElementType.POTION_ICONS, RenderGameOverlayEvent.ElementType.VIGNETTE, RenderGameOverlayEvent.ElementType.HELMET};

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (nullCheck()) return;

        for (RenderGameOverlayEvent.ElementType element : elementType) {
            if (e.getType() == element) {
                e.setCanceled(true);
            }
        }
    }
}

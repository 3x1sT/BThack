package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.System.Client;
import com.bt.BThack.System.ConfigInit.ConfigInit;
import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.SpeedMathThread;
import com.bt.BThack.impl.HudComponents.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class HUD extends Module {
    public HUD() {
        super("HUD",
                "Enabling and configuring HUD modules.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                true
        );

        allowRemapKeyCode = false;

        Client.hudComponents.addAll(Arrays.asList(
                new WatermarkComponent(this),
                new FPSComponent(this),
                new CoordinatesComponent(this),
                new RotationComponent(this),
                new DirectionComponent(this),
                new ServerIpComponent(this),
                new SpeedComponent(this),
                new PingComponent(this),
                new TPSComponent(this),
                new PlayerCountComponent(this),
                new InventoryComponent(this),
                new ArmorComponent(this),
                new BiomeComponent(this),
                new RealTimeComponent(this),


                new DimensionComponent(this),
                new DurabilityComponent(this),
                new CrystalCountComponent(this),
                new EXPCountComponent(this),
                new GappleCountComponent(this),
                new TotemCountComponent(this),
                new PlayerComponent(this),

                new TextRadarComponent(this),


                new ArrayListComponent(this)
        ));

        try {
            ConfigInit.loadHudComponents();
        } catch (IOException ignored) {
        }
    }

    boolean isRendered = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        for (HudComponent component : Client.hudComponents) {
            component.setToggled(getCheckbox(this.name, component.name));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRender(RenderGameOverlayEvent.Post e) {
        for (HudComponent component : Client.hudComponents) {
            if (component.toggled) {
                if (Objects.requireNonNull(e.getType()) == RenderGameOverlayEvent.ElementType.TEXT) {
                    component.render(HudComponent.RenderType.TEXT);
                    isRendered = true;
                } else if (Objects.requireNonNull(e.getType()) == RenderGameOverlayEvent.ElementType.HOTBAR && isRendered) {
                    component.render(HudComponent.RenderType.IMAGE);
                    isRendered = false;
                }
            }
        }

        if (!SpeedMathThread.active) {
            new SpeedMathThread().start();
        }
    }
}

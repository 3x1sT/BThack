package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class NoRender extends Module {
    public NoRender() {
        super("NoRender",
                "Disables rendering.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addCheckbox("Explosions", this, true);
        addCheckbox("Particles", this, false);
        addCheckbox("Items", this, false);
        addCheckbox("Fire", this, true);
        addCheckbox("Water", this, true);
        addCheckbox("Blocks", this, true);
        addCheckbox("Tutorial", this, true);
        addCheckbox("Falling Blocks", this, false);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive e) {
        if (nullCheck()) return;

        if ((getCheckbox(this.name, "Explosions") && e.getPacket() instanceof SPacketExplosion) ||
                (getCheckbox(this.name, "Particles") && e.getPacket() instanceof SPacketParticles)

        ) {
            e.setCanceled(true);
        }

        if (e.getPacket() instanceof SPacketSpawnObject) {
            SPacketSpawnObject packet = (SPacketSpawnObject) e.getPacket();

            switch (packet.getType()) {
                case 2:
                    e.setCanceled(getCheckbox(this.name, "Items"));
                    break;
                case 70:
                    e.setCanceled(getCheckbox(this.name, "Falling Blocks"));
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        if (getCheckbox(this.name, "Tutorial")) {
            mc.gameSettings.tutorialStep = TutorialSteps.NONE;
        }

        if (e.phase == TickEvent.Phase.END && getCheckbox(this.name, "Items")) {
            for (Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityItem)
                    entity.setDead();
            }
        }
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent e) {
        if (nullCheck()) return;

        if (
                (getCheckbox(this.name, "Water") && e.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER) ||
                (getCheckbox(this.name, "Blocks") && e.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK)
        ) {
            e.setCanceled(true);
        }

        if (!Client.getModuleByName("Africa").isEnabled()) {
            if ((getCheckbox(this.name, "Fire") && e.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE)) {
                e.setCanceled(true);
            }
        }
    }
}

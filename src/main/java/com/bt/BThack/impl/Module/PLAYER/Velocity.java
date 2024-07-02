package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

/**
 * @see com.bt.BThack.mixins.mixin.MixinEntityPlayerSP
 * @see com.bt.BThack.mixins.mixin.MixinEntity
 * @see com.bt.BThack.mixins.mixin.MixinBlockLiquid
 */


public class Velocity extends Module {
    public Velocity() {
        super("Velocity",
                "Disables player knockback.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addCheckbox("Velocity", this, true);
        addSlider("Velocity", true,"Velocity Vertical", this, 0.0,0.0,100.0,true);
        addSlider("Velocity", true,"Velocity Horizontal", this, 0.0,0.0,100.0,true);

        addCheckbox("Explosions", this, true);
        addSlider("Explosions", true,"Expl Vertical", this, 0.0,0.0,100.0,true);
        addSlider("Explosions", true,"Expl Horizontal", this, 0.0,0.0,100.0,true);

        addCheckbox("NoPush Entities", this, true);
        addCheckbox("NoPush Liquids", this, true);
        addCheckbox("NoPush Blocks", this, true);
    }

    @SubscribeEvent
    public void onUpdate(PacketEvent.Receive e) {
        if (nullCheck()) return;

        boolean velocity = getCheckbox(this.name, "Velocity");
        boolean explosions = getCheckbox(this.name, "Explosions");
        int velV = (int) getSlider(this.name, "Velocity Vertical");
        int velH = (int) getSlider(this.name, "Velocity Horizontal");
        float explV = (float) getSlider(this.name, "Expl Vertical");
        float explH = (float) getSlider(this.name, "Expl Horizontal");

        if (nullCheck()) return;

        if (e.getPacket() instanceof SPacketEntityVelocity && velocity) {
            SPacketEntityVelocity sPacketEntityVelocity = (SPacketEntityVelocity) e.getPacket();

            if (sPacketEntityVelocity.getEntityID() == mc.player.getEntityId()) {
                if (velH == 0 && velV == 0) {
                    e.setCanceled(true);
                } else {
                    velV = velV/100;
                    velH = velH/100;
                    sPacketEntityVelocity.motionX *= velH;
                    sPacketEntityVelocity.motionY *= velV;
                    sPacketEntityVelocity.motionZ *= velH;
                }
            }
        }

        if (e.getPacket() instanceof SPacketExplosion && explosions) {
            SPacketExplosion sPacketExplosion = (SPacketExplosion) e.getPacket();

            if (explH == 0.0f && explV == 0.0f) {
                e.setCanceled(true);
            } else {
                explV = explV/100;
                explH = explH/100;
                sPacketExplosion.motionX *= explH;
                sPacketExplosion.motionY *= explV;
                sPacketExplosion.motionZ *= explH;
            }
        }
    }
}

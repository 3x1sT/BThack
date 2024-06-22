package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Objects;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals",
                "Make all hits critical.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );
        ArrayList<String> options = new ArrayList<>();

        options.add("Packet");
        options.add("Bypass");
        options.add("MiniJump");
        options.add("Jump");
        addMode("Mode", this, options, "Mode");
    }

    @SubscribeEvent
    public void onUpdate(PacketEvent.Send e) {
        if (nullCheck()) return;

        if (e.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) e.getPacket();

            if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                if (cPacketUseEntity.getEntityFromWorld(mc.world) instanceof EntityEnderCrystal) return;

                if (!mc.player.onGround || mc.player.isInWater() || mc.player.isInLava()) return;

                switch (getMode(this.name, "Mode")) {
                    case "Packet":
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer());
                        mc.player.onCriticalHit(Objects.requireNonNull(cPacketUseEntity.getEntityFromWorld(mc.world)));
                        break;
                    case "Bypass":
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1625, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 4.0E-6, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-6, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer());
                        mc.player.onCriticalHit(Objects.requireNonNull(cPacketUseEntity.getEntityFromWorld(mc.world)));
                        break;
                    case "MiniJump":
                        mc.player.jump();
                        mc.player.motionY = 0.25;
                        break;
                    case "Jump":
                        mc.player.jump();
                        break;
                }
            }
        }
    }
}

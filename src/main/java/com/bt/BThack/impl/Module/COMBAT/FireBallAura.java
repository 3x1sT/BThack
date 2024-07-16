package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.bt.BThack.api.Utils.Modules.KillAuraUtils;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.Set;

public class FireBallAura extends Module {

    public FireBallAura() {
        super("FireBallAura",
                "Throws off the fireballs coming at you.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        addSlider("Range", this, 3, 2, 6, false);
        addCheckbox("Packet Rotate", this, true);
    }

    private final Set<Entity> fireBalls = Sets.newHashSet();

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        Entity fireBall = mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityFireball).min(Comparator.comparing(
                entity -> entity.getDistance(mc.player))).filter(entity -> entity.getDistance(mc.player) <= getSlider(this.name, "Range")).orElse(null);

        if (fireBall != null) {
            if (!fireBalls.contains(fireBall)) {
                if (getCheckbox(this.name, "Packet Rotate")) {
                    float[] oldRot = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(AimBotUtils.rotations(fireBall)[0], AimBotUtils.rotations(fireBall)[1], mc.player.onGround));

                    KillAuraUtils.DelayAttack(fireBall);
                    fireBalls.add(fireBall);

                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(oldRot[0], oldRot[1], mc.player.onGround));
                } else {
                    KillAuraUtils.DelayAttack(fireBall);
                    fireBalls.add(fireBall);
                }
            }
        }

        fireBalls.removeIf(entity -> !mc.world.loadedEntityList.contains(entity));
    }
}

package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

public class NoFriendDamage extends Module {
    public NoFriendDamage() {
        super("NoFriendDamage",
                "You can't hit your friends.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send e) {
        if (nullCheck()) return;

        if (e.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) e.getPacket();

            if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK && FriendsUtils.isFriend(Objects.requireNonNull(cPacketUseEntity.getEntityFromWorld(mc.world)).getName())) {
                e.setCanceled(true);
            }
        }
    }
}

package com.bt.BThack.api.Managers;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.impl.Events.OnUpdateWalkingPlayerEvent;
import com.bt.BThack.impl.Events.PacketEvent;
import com.bt.BThack.impl.Module.MOVEMENT.ElytraFlight.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class PlayerPacketManager implements Mc {

    private static final ArrayList<Packet> packetMap = new ArrayList<>();

    private static Vec3d serverSidePosition = Vec3d.ZERO;
    private static Vec3d prevServerSidePosition = Vec3d.ZERO;

    private static Vec2f serverSideRotation = Vec2f.ZERO;
    private static Vec2f prevServerSideRotation = Vec2f.ZERO;

    //private static Vec2f clientSidePitch = Vec2f.ZERO;

    public static Vec3d getServerSidePosition() {
        return serverSidePosition;
    }

    public static Vec3d getPrevServerSidePosition() {
        return prevServerSidePosition;
    }

    public static Vec2f getServerSideRotation() {
        return serverSideRotation;
    }

    public static Vec2f getPrevServerSideRotation() {
        return prevServerSideRotation;
    }

    public static void sendPlayerPacket(Packet.Builder builder) {
        Packet packet = builder.build();
        if (packet != null) {
            sendPlayerPacket(packet);
        }
    }

    public static void sendPlayerPacket(Packet packet) {
        packetMap.clear();
        packetMap.add(packet);
    }



    @SubscribeEvent
    public void onUpdateWalking(OnUpdateWalkingPlayerEvent event) {
        if (event.phase != OnUpdateWalkingPlayerEvent.Phase.PERI || packetMap.isEmpty()) return;

        Packet packet = packetMap.iterator().next();

        event.apply(packet);
        packetMap.remove(packet);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        prevServerSidePosition = serverSidePosition;
        prevServerSideRotation = serverSideRotation;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Receive event) {
        if (event.isCanceled() || !(event.getPacket() instanceof CPacketPlayer)) return;

        CPacketPlayer packet = (CPacketPlayer) event.getPacket();
        if (packet.moving) {
            serverSidePosition = new Vec3d(packet.x, packet.y, packet.z);
        }

        if (packet.rotating) {
            serverSideRotation = new Vec2f(packet.yaw, packet.pitch);
            if (mc.player != null) {
                mc.player.setRotationYawHead(packet.yaw);
            }
        }
    }

    @SubscribeEvent
    public void onPostSendPacket(PacketEvent.Send.Post event) {
        if (event.isCanceled() || !(event.getPacket() instanceof CPacketPlayer)) {
            return;
        }

        CPacketPlayer packet = (CPacketPlayer) event.getPacket();
        if (packet.moving) {
            serverSidePosition = new Vec3d(packet.x, packet.y, packet.z);
        }

        if (packet.rotating) {
            serverSideRotation = new Vec2f(packet.yaw, packet.pitch);
            mc.player.setRotationYawHead(packet.yaw);
        }
    }
}
package com.bt.BThack.impl.Events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @see com.bt.BThack.mixins.mixin.MixinNetworkManager
 */

@Cancelable
public class PacketEvent extends Event {
    private Packet<?> packet;

    public PacketEvent(Packet<?> packet) {

        this.packet = packet;
    }

    public final Packet<?> getPacket() {

        return this.packet;
    }


    public final Packet<?> setPacket(Packet<?> packet) {

        return this.packet = packet;
    }

    public static class Send extends PacketEvent {

        public Send(Packet<?> packet) {

            super(packet);
        }
    }

    public static class Receive extends PacketEvent {

        public Receive(Packet<?> packet) {

            super(packet);
        }
    }
}

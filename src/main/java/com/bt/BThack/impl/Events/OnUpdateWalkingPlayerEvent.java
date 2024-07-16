package com.bt.BThack.impl.Events;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.impl.Module.MOVEMENT.ElytraFlight.Packet;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class OnUpdateWalkingPlayerEvent extends Event implements Mc {
    public boolean moving;
    public boolean rotating;
    public Vec3d position;
    public Vec2f rotation;
    public final Phase phase;

    public boolean cancelAll;

    private OnUpdateWalkingPlayerEvent(boolean moving, boolean rotating, Vec3d position, Vec2f rotation, Phase phase) {
        this.moving = moving;
        this.rotating = rotating;
        this.position = position;
        this.rotation = rotation;
        this.phase = phase;
    }

    public OnUpdateWalkingPlayerEvent(boolean moving, boolean rotating, Vec3d position, Vec2f rotation) {
        this(moving, rotating, position, rotation, Phase.PRE);
    }

    public boolean isMoving() {
        return this.moving;
    }

    private void setRotating(boolean rotating) {
        this.rotating = rotating;
    }

    public boolean isRotating() {
        return this.rotating;
    }

    private void setCancelAll(boolean cancelAll) {
        this.cancelAll = cancelAll;
    }

    public OnUpdateWalkingPlayerEvent nextPhase() {
        Phase phase = this.phase;

        switch (phase) {
            case PRE:
                phase = Phase.PERI;
                break;
            case PERI:
            case  POST:
                phase = Phase.POST;
                break;
        }


        return new OnUpdateWalkingPlayerEvent(moving, rotating, position, rotation, phase);
    }

    public void apply(Packet packet) {
        this.setCanceled(true);

        if (packet.moving != null) {
            moving = packet.moving;
            if (packet.position != null) {
                position = packet.position;
            }
        }

        if (packet.rotating != null) {
            rotating = packet.rotating;
            if (packet.rotation != null) {
                rotation = packet.rotation;
            }
        }

        cancelAll = packet.cancelAll;
    }

    public enum Phase {
        PRE,
        PERI,
        POST
    }
}
package com.bt.BThack.impl.Events;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderEntityEvent extends Event {
    public final Entity entity;
    public final OnUpdateWalkingPlayerEvent.Phase phase;

    private RenderEntityEvent(Entity entity, OnUpdateWalkingPlayerEvent.Phase phase) {
        this.entity = entity;
        this.phase = phase;
    }


    public String getProfilerName() {
        return "kbRenderEntity" + phase.name();
    }


    public RenderEntityEvent nextPhase() {
        throw new UnsupportedOperationException();
    }

    public static class All extends RenderEntityEvent {
        public All(Entity entity, OnUpdateWalkingPlayerEvent.Phase phase) {
            super(entity, phase);
        }
    }

    public static class Model extends RenderEntityEvent {
        public Model(Entity entity, OnUpdateWalkingPlayerEvent.Phase phase) {
            super(entity, phase);
        }
    }

    private static boolean renderingEntities = false;

    public static void setRenderingEntities(boolean in) {
        renderingEntities = in;
    }

    public static boolean isRenderingEntities() {
        return renderingEntities;
    }
}

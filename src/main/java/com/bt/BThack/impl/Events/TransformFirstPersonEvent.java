package com.bt.BThack.impl.Events;

import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class TransformFirstPersonEvent extends Event {
    private final EnumHandSide enumHandSide;

    public TransformFirstPersonEvent(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return enumHandSide;
    }

    public static class Pre extends TransformFirstPersonEvent {
        public Pre(EnumHandSide enumHandSide) {
            super(enumHandSide);
        }
    }

    public static class Post extends TransformFirstPersonEvent {
        public Post(EnumHandSide enumHandSide) {
            super(enumHandSide);
        }
    }
}
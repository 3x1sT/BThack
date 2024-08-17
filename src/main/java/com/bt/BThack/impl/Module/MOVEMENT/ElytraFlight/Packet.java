package com.bt.BThack.impl.Module.MOVEMENT.ElytraFlight;

import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class Packet {
    public final Boolean moving;
    public final Boolean rotating;
    public final Vec3d position;
    public final Vec2f rotation;
    public final boolean cancelAll;

    private Packet(Boolean moving, Boolean rotating, Vec3d position, Vec2f rotation, boolean cancelAll) {
        this.moving = moving;
        this.rotating = rotating;
        this.position = position;
        this.rotation = rotation;
        this.cancelAll = cancelAll;
    }

    public static class Builder {
        private Vec3d position = null;
        private Boolean moving = null;
        private Vec2f rotation = null;
        private Boolean rotating = null;
        private boolean cancelAll = false;
        private boolean empty = true;

        public Builder move(Vec3d position) {
            this.position = position;
            this.moving = true;
            this.empty = false;
            return this;
        }

        public Builder rotate(Vec2f rotation) {
            this.rotation = rotation;
            this.rotating = true;
            this.empty = false;
            return this;
        }

        public Builder cancelAll() {
            this.cancelAll = true;
            this.empty = false;
            return this;
        }

        public Builder cancelMove() {
            this.position = null;
            this.moving = false;
            this.empty = false;
            return this;
        }

        public Builder cancelRotate() {
            this.rotation = null;
            this.rotating = false;
            this.empty = false;
            return this;
        }

        public Packet build() {
            if (!empty) {
                return new Packet(moving, rotating, position, rotation, cancelAll);
            } else {
                return null;
            }
        }
    }
}
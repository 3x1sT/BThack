package com.bt.BThack.api.Effect;

public class EffectBase {
    private final int startX;
    private final int startY;

    private final int endX;
    private final int endY;

    private int currentX;
    private int currentY;

    public EffectBase(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;

        this.endX = endX;
        this.endY = endY;
    }

    public void tick() {}



    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public int getEndX() {
        return this.endX;
    }

    public int getEndY() {
        return this.endY;
    }
}

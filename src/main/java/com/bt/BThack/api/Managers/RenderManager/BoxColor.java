package com.bt.BThack.api.Managers.RenderManager;

public class BoxColor {
    public final float lineRed;
    public final float lineGreen;
    public final float lineBlue;
    public final float lineAlpha;

    public final float boxRed;
    public final float boxGreen;
    public final float boxBlue;
    public final float boxAlpha;

    public BoxColor(float lineRed, float lineGreen, float lineBlue, float lineAlpha, float boxRed, float boxGreen, float boxBlue, float boxAlpha) {
        this.lineRed = lineRed;
        this.lineGreen = lineGreen;
        this.lineBlue = lineBlue;
        this.lineAlpha = lineAlpha;

        this.boxRed = boxRed;
        this.boxGreen = boxGreen;
        this.boxBlue = boxBlue;
        this.boxAlpha = boxAlpha;
    }
}

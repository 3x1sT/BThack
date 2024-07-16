package com.bt.BThack.api.Utils.Render;

public class RainbowUtils {
    public static float[] getRainbowSpeed(int rainbowType) {
        int delay;
        float speed;
        switch (rainbowType) {
            case 1:
            default:
                speed = 1;
                delay = 100;
                break;
            case 2:
                speed = 1;
                delay = 200;
                break;
            case 3:
                speed = 1;
                delay = 300;
                break;
            case 4:
                speed = 1;
                delay = 500;
                break;
            case 5:
                speed = 0.1f;
                delay = 750;
                break;
            case 6:
                speed = 0.1f;
                delay = 700;
                break;
            case 7:
                speed = 0.1f;
                delay = 600;
                break;
            case 8:
                speed = 0.1f;
                delay = 850;
                break;
        }
        return new float[] {speed, delay};
    }

    public static float[] getRainbowRectSpeed(int rainbowType) {
        int delay;
        float speed;

        switch (rainbowType) {
            default:
                speed = 1;
                delay = 40;
                break;
            case 2:
                speed = 0.5f;
                delay = 20;
                break;
            case 3:
                speed = 0.2f;
                delay = 10;
                break;
            case 4:
                speed = 0.1f;
                delay = 725;
                break;
        }
        return new float[]{speed, delay};
    }
}

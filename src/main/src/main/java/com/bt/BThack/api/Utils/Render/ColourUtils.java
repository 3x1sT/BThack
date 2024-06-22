package com.bt.BThack.api.Utils.Render;

import java.awt.*;

public class ColourUtils {
    public static int rainbow(int  delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }

    public static int rainbow(int delay, float speed) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        float rSpeed = 360 * speed;
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / rSpeed), 0.5f, 1f).getRGB();
    }

    public static int rainbowType(int type) {
        float speed = RainbowUtils.getRainbowSpeed(type)[0];
        int delay = (int)RainbowUtils.getRainbowSpeed(type)[1];

        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        float rSpeed = 360 * speed;
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / rSpeed), 0.5f, 1f).getRGB();
    }

    public static int rainbowType(int type, float counter) {
        float speed = RainbowUtils.getRainbowSpeed(type)[0];
        int delay = (int)RainbowUtils.getRainbowSpeed(type)[1];

        delay = (int)(delay * counter);

        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        float rSpeed = 360 * speed;
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / rSpeed), 0.5f, 1f).getRGB();
    }
}

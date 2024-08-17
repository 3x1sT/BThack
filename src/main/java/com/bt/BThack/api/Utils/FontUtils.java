package com.bt.BThack.api.Utils;

import net.minecraft.client.gui.FontRenderer;

//lol
public class FontUtils {
    public static void drawCenteredString(FontRenderer font, String text, int x, int y, int color) {
        font.drawString(text, (x - font.getStringWidth(text) / 2), y, color);
    }
}

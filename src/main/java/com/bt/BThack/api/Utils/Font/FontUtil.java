package com.bt.BThack.api.Utils.Font;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;

public final class FontUtil implements Mc {
    public static final BThackFontRenderer ubuntuFont = new BThackFontRenderer("Ubuntu", 17.0f);
    public static final BThackFontRenderer latoFont = new BThackFontRenderer("Lato", 17.0f);
    public static final BThackFontRenderer verdanaFont = new BThackFontRenderer("Verdana", 17.0f);
    public static final BThackFontRenderer comfortaaFont = new BThackFontRenderer("Comfortaa", 17.0f);
    public static final BThackFontRenderer subtitleFont = new BThackFontRenderer("Subtitle", 17.0f);
    public static final BThackFontRenderer comicSansFont = new BThackFontRenderer("ComicSans", 17.0f);

    public static BThackFontRenderer getCurrentCustomFont() {
        switch (Module.getMode("Font", "Font")) {
            case "Ubuntu":
                return ubuntuFont;
            case "Lato":
                return latoFont;
            case "Verdana":
                return verdanaFont;
            case "Comfortaa":
                return comfortaaFont;
            case "Subtitle":
                return subtitleFont;
            case "ComicSans":
                return comicSansFont;
        }

        return ubuntuFont;
    }

    public static void drawText(String text, float x, float y, int colour) {
        if (Module.getCheckbox("Font", "Shadow")) {
            if (Client.getModuleByName("Font").isEnabled()) {
                getCurrentCustomFont().drawStringWithShadow(Module.getCheckbox("Font", "Lowercase") ? text.toLowerCase() : text, x, y, colour);
            } else {
                mc.fontRenderer.drawStringWithShadow(Module.getCheckbox("Font", "Lowercase") ? text.toLowerCase() : text, x, y, colour);
            }
        } else {
            if (Client.getModuleByName("Font").isEnabled()) {
                getCurrentCustomFont().drawString(Module.getCheckbox("Font", "Lowercase") ? text.toLowerCase() : text, x, y, colour);
            } else {
                mc.fontRenderer.drawString(Module.getCheckbox("Font", "Lowercase") ? text.toLowerCase() : text, (int) x, (int) y, colour);
            }
        }
    }

    public static void drawTextNoShadow(String text, float x, float y, int colour) {
        if (Client.getModuleByName("Font").isEnabled()) {
            getCurrentCustomFont().drawString(Module.getCheckbox("Font", "Lowercase") ? text.toLowerCase() : text, x, y, colour);
        } else {
            mc.fontRenderer.drawString(Module.getCheckbox("Font", "Lowercase") ? text.toLowerCase() : text, (int) x, (int) y, colour);
        }
    }

    public static float getStringWidth(String text) {
        return getCurrentCustomFont().getStringWidth(text);
    }

    public static float getStringHeight(String text) {
        return getCurrentCustomFont().getStringHeight(text);
    }
}
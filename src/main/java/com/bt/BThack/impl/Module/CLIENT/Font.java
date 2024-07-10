package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class Font extends Module {
    public Font() {
        super("Font",
                "Enabling and customizing the client's custom font.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                true
        );

        allowRemapKeyCode = false;

        ArrayList<String> font = new ArrayList<>(Arrays.asList(
                "Ubuntu",
                "Lato",
                "Verdana",
                "Comfortaa",
                "Subtitle",
                "ComicSans"
        ));

        addMode("Font", this, font);

        addCheckbox("Shadow", this, true);
        addCheckbox("Lowercase", this, false);
        addCheckbox("Override Minecraft", this, false);
    }
}

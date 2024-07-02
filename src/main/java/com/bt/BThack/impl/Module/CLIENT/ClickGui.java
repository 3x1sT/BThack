package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.BThack;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Themes.ColourThemes.ColourTheme;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGui extends Module {

    public static int x = 0;

    public ClickGui() {
        super("ClickGui",
                "Gui with selection and configuration of functions.",
                Keyboard.KEY_RSHIFT,
                Category.CLIENT,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        for (ColourTheme theme : BThack.instance.colourThemeManager.getColourThemes()) {
            options.add(theme.getName());
        }

        addMode("Active theme", this, options);

        addSlider("Custom Color", true,"Red", this, 25,0,255,true);
        addSlider("Custom Color", true,"Green", this, 28,0,255,true);
        addSlider("Custom Color", true,"Blue", this, 255,0,255,true);
        addCheckbox("Rainbow", false,"Custom Color", this, false);
        addCheckbox("Rainbow", this, false);
        addSlider("Rainbow", true,"Rainbow speed", this, 2, 1, 4, true);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (mc.currentScreen == null) {
            mc.displayGuiScreen(BThack.instance.clickGui);
        }
        toggle();
    }
}

package com.bt.BThack.api.Themes.ColourThemes;

import com.bt.BThack.BThack;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Objects;

public class ColourThemeUpdate {

    @SubscribeEvent
    public void onTheme(TickEvent.ClientTickEvent e) {
        for (ColourTheme theme : BThack.instance.colourThemeManager.getColourThemes()) {
            if (Objects.equals(Module.getMode("ClickGui", "Active theme"), theme.getName())) {
                Client.colourTheme = theme;
            }
        }
    }
}

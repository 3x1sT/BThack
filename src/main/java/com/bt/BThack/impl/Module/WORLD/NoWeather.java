package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class NoWeather extends Module {
    public NoWeather() {
        super("NoWeather",
                "Turns off the weather.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

    }
}

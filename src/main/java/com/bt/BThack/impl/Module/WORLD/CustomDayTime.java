package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Objects;

public class CustomDayTime extends Module {

    public CustomDayTime() {
        super("CustomDayTime",
                "Sets custom day time.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("Day");
        options.add("Night");
        options.add("Morning");
        options.add("Sunset");
        options.add("Spin");

        addMode("Mode", this, options, "Mode");
        addSlider("Spin speed", this, 50,10,400,true);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        String Mode = getMode(this.name, "Mode");
        double speed = getSlider(this.name, "Spin speed");

        if (this.isEnabled()) {
            if (Objects.equals(Mode, "Day")) {
                if (mc.world.getWorldTime() != 5000)
                    mc.world.setWorldTime(5000);
            }
            if (Objects.equals(Mode, "Night")) {
                if (mc.world.getWorldTime() != 17000)
                    mc.world.setWorldTime(17000);
            }
            if (Objects.equals(Mode, "Morning")) {
                if (mc.world.getWorldTime() != 0)
                    mc.world.setWorldTime(0);
            }
            if (Objects.equals(Mode, "Sunset")) {
                if (mc.world.getWorldTime() != 13000)
                    mc.world.setWorldTime(13000);
            }
            if (Objects.equals(Mode, "Spin")) {
                long newTime = (long) (mc.world.getWorldTime() + speed);
                mc.world.setWorldTime(newTime);
            }
        }
    }
}

package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BTbot.impl.AntiAFK.Start.StartAntiAFK;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AntiAFK extends Module {

    private static boolean correct = false;

    public AntiAFK() {
        super("AntiAFK",
                "Action randomizer to simulate player activity.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Walking radius", this, 3.0,1,10,false);
        addSlider("Delay(Seconds)", this, 3.0,0.5,10,false);
        addSlider("Message size", this, 15,5,30,true);
    }

    @Override
    public void onEnable() {
        if (!StartAntiAFK.active) {
            correct = true;
            StartAntiAFK.start();
        } else {
            ChatUtils.sendMessage(ChatFormatting.YELLOW + "AntiAFK is already enabled. Please turn it off before using the module.");
            correct = false;
            setToggled(false);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (correct) {
            StartAntiAFK.walkRadius = getSlider(this.name, "Walking radius");
            StartAntiAFK.messageSize = (int) getSlider(this.name, "Message size");
            StartAntiAFK.delay = getSlider(this.name, "Delay(Seconds)");
        }
    }

    @Override
    public void onDisable() {
        if (correct) {
            if (StartAntiAFK.active) {
                StartAntiAFK.stop();
            }
        }
    }
}

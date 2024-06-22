package com.bt.BThack.impl.Module.COMBAT.KillAura;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Module.COMBAT.AimBot;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class KillAura extends Module {

    public KillAura() {
        super("KillAura",
                "Attacks players within a given radius.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("Aura");
        options.add("TriggerBot");

        ArrayList<String> attackOptions = new ArrayList<>();

        attackOptions.add("CoolDown");
        attackOptions.add("Spam");
        attackOptions.add("Delay");

        addMode("Mode", this, options, "Mode");
        addMode("AttackMode", this, attackOptions, "Attack Mode");
        addSlider("Range", this, 4.0, 1, 10, false);
        addCheckbox("AimBot", this, true);
        addSlider("Delay(Second)", this, 1.4, 0.1, 4, false);
        addCheckbox("Players", this, true);
        addCheckbox("Mobs", this, true);
        addCheckbox("Teammates", this, false);
        addCheckbox("Friends", this, false);
        addCheckbox("Allies", this, false);
        addCheckbox("Ignore Walls", this, false);
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (getCheckbox(this.name, "Aimbot")) {
                AimBot.rotate(this.name);
            }
            switch (getMode(this.name, "Mode")) {
                case "Aura":
                    if (AuraThread.active == 0) {
                        new AuraThread().start();
                    }
                    break;
                case "TriggerBot":
                    if (TriggerBotThread.active == 0) {
                        new TriggerBotThread().start();
                    }
                    break;
            }
        }
    }
}
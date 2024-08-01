package com.bt.BThack.impl.Module.COMBAT.KillAura;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.impl.Events.PacketEvent;
import com.bt.BThack.impl.Module.COMBAT.AimBot;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class KillAura extends Module {

    private AuraThread aura = new AuraThread();
    private TriggerBotThread triggerBot = new TriggerBotThread();

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

        addMode("Mode", this, options);
        addMode("AttackMode", this, attackOptions);
        addSlider("Mode", "Aura","Range", this, 4.0, 1, 10, false);
        addCheckbox("AimBot", this, true);
        addSlider("AttackMode", "Delay","Delay(Second)", this, 1.4, 0.1, 4, false);
        addCheckbox("Players", this, true);
        addCheckbox("Mobs", this, true);
        addCheckbox("Teammates", this, false);
        addCheckbox("Friends", this, false);
        addCheckbox("Ignore Walls", this, false);

        ClansUtils.addClanManagerInModule(this);
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
        if (nullCheck()) return;

        String mode = getMode(this.name, "Mode");

        arrayListInfo = mode + (!mode.equals("TriggerBot") ? "; " + getSlider(this.name, "Range") : "");

        if (mc.player != null) {
            if (getCheckbox(this.name, "Aimbot")) {
                AimBot.rotate(this.name);
            }
            switch (mode) {
                case "Aura":
                    if (!aura.isAlive()) {
                        aura = new AuraThread();
                        aura.start();
                    }
                    break;
                case "TriggerBot":
                    if (!triggerBot.isAlive()) {
                        triggerBot = new TriggerBotThread();
                        triggerBot.start();
                    }
                    break;
            }
        }
    }

    @SubscribeEvent
    public void onSendPacket(PacketEvent.Send e) {
        if (nullCheck()) return;

        if (e.getPacket() instanceof CPacketEntityAction) {
            CPacketEntityAction packet = (CPacketEntityAction) e.getPacket();
            if (packet.getAction() == CPacketEntityAction.Action.STOP_SPRINTING && mc.player.isSprinting()) {
                e.setCanceled(true);
            }
        }
    }
}
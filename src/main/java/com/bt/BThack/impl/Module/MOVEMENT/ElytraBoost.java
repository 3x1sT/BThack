package com.bt.BThack.impl.Module.MOVEMENT;


import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Entity.PlayerUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ElytraBoost extends Module {

    public ElytraBoost() {
        super("ElytraBoost",
                "Allows you to increase speed when flying elytres.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Max speed", this, 35, 20, 50, false);
        addSlider("Boost Strength", this, 0.01, 0.001, 0.07, false);
        addCheckbox("Y motion also", this, false);
        addSlider("Start boost pitch", this, 45, 80, 10, false);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        super.onEnable();

        Client.getModuleByName("ElytraFlight").setToggled(false);
        Client.getModuleByName("NewElytraFlight").setToggled(false);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        if (PlayerUtil.getEntitySpeed(mc.player) < getSlider(this.name, "Max speed") && mc.player.isElytraFlying()) {
            if (mc.player.rotationPitch > getSlider(this.name, "Start boost pitch")) {
                double boost = 1 + getSlider(this.name, "Boost Strength");
                mc.player.motionX *= boost;
                mc.player.motionZ *= boost;
                if (getCheckbox(this.name, "Y motion also"))
                    mc.player.motionY *= boost;
            }
        }
    }
}

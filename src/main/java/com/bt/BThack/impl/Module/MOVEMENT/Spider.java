package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Spider extends Module {
    public Spider() {
        super("Spider",
                "The player can climb walls like a spider.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Speed", this, 0.25,0.05,1,false);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (mc.player.collidedHorizontally && mc.player.movementInput.moveForward > 0) {
                mc.player.collidedVertically = true;
                mc.player.onGround = true;
                mc.player.motionY = getSlider(this.name, "Speed");
            }
        }
    }
}

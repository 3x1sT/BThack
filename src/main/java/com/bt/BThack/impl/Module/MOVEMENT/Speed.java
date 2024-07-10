package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    public Speed() {
        super("Speed",
                "Makes the player faster.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Speed", this, 0.25,0.1,1,false);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (mc.player.onGround && mc.player.moveForward > 0 && !mc.player.isInWater() && !mc.player.isInLava()) {
                double speed = getSlider(this.name, "Speed");

                mc.player.setSprinting(true);
                mc.player.motionY = 0.1;

                float yaw = mc.player.rotationYaw * 0.0174532920F;

                mc.player.motionX -= MathHelper.sin(yaw) * (speed / 5);
                mc.player.motionZ += MathHelper.cos(yaw) * (speed / 5);
            }
        }
    }
}

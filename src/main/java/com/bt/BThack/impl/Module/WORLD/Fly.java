package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {
    public Fly() {
        super("Fly",
                "Allows the player to fly.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );
    }

   @SubscribeEvent
   public void onPlayerTick(TickEvent.PlayerTickEvent e) {
       if (nullCheck()) return;

       mc.player.capabilities.allowFlying = true;
       if (mc.player.collidedVertically) {
           mc.player.motionY = 0.025;
           mc.player.capabilities.isFlying = true;
       }
       //mc.player.onGround = true;
   }

    @Override
    public void onDisable() {
        super.onDisable();

        if (nullCheck()) return;

        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.allowFlying = false;
    }
}

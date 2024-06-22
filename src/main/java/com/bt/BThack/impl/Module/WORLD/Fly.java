package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Objects;

public class Fly extends Module {
    public Fly() {
        super("Fly",
                "Allows the player to fly.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("Creative");
        options.add("WellMore");

        addMode("Mode", this, options, "Mode");
    }

   @SubscribeEvent
   public void onPlayerTick(TickEvent.PlayerTickEvent e) {
       if (nullCheck()) return;

       String Mode = Module.getMode(this.name, "Mode");

       if (mc.player != null) {
           if (Objects.equals(Mode, "WellMore")) {
               float speed = 2;

               mc.player.noClip = true;
               mc.player.fallDistance = 0;
               mc.player.onGround = false;

               mc.player.capabilities.isFlying = false;

               mc.player.motionX = 0;
               mc.player.motionY = 0;
               mc.player.motionZ = 0;

               mc.player.jumpMovementFactor = speed;

               if (mc.gameSettings.keyBindJump.isKeyDown()) {
                   mc.player.motionY += speed;
               }
               if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                   mc.player.motionY -= speed;
               }
           } else {
               mc.player.capabilities.allowFlying = true;
               if (mc.player.collidedVertically) {
                   mc.player.motionY = 0.1;
                   mc.player.capabilities.isFlying = true;
               }
               mc.player.onGround = true;
           }
       }
   }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        super.onDisable();
         mc.player.capabilities.isFlying = false;
        mc.player.capabilities.allowFlying = false;
    }
}

package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class HitSound extends Module {
    public HitSound() {
        super("HitSound",
                "Produces sound when struck.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("Ding");
        options.add("Meow");
        options.add("Villager");
        options.add("Enderman");
        options.add("EnderDragon");
        options.add("Blaze");
        options.add("Chicken");
        options.add("Cow");

        addMode("Sound", this, options);
    }

    @SubscribeEvent
    public void onUpdate(PacketEvent.Send e) {
        if (nullCheck()) return;

        if (e.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) e.getPacket();

            if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (cPacketUseEntity.getEntityFromWorld(mc.world) instanceof EntityEnderCrystal) return;

                switch (getMode(this.name, "Sound")) {
                    case "Ding":
                        mc.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        break;
                    case "Meow":
                        mc.player.playSound(SoundEvents.ENTITY_CAT_AMBIENT, 1, 1);
                        break;
                    case "Villager":
                        mc.player.playSound(SoundEvents.ENTITY_VILLAGER_HURT, 1, 1);
                        break;
                    case "Enderman":
                        mc.player.playSound(SoundEvents.ENTITY_ENDERMEN_HURT, 1, 1);
                        break;
                    case "EnderDragon":
                        mc.player.playSound(SoundEvents.ENTITY_ENDERDRAGON_HURT, 1, 1);
                        break;
                    case "Blaze":
                        mc.player.playSound(SoundEvents.ENTITY_BLAZE_HURT, 1, 1);
                        break;
                    case "Chicken":
                        mc.player.playSound(SoundEvents.ENTITY_CHICKEN_HURT, 1, 1);
                        break;
                    case "Cow":
                        mc.player.playSound(SoundEvents.ENTITY_COW_HURT, 1, 1);
                        break;
                }
            }
        }
    }
}
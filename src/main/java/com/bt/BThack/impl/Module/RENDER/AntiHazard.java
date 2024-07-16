package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AntiHazard extends Module {
    public AntiHazard() {
        super("AntiHazard",
                "Disables nausea and blindness effects.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (nullCheck()) return;

        if (mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            mc.player.removePotionEffect(MobEffects.BLINDNESS);
        }
        if (mc.player.isPotionActive(MobEffects.NAUSEA)) {
            mc.player.removePotionEffect(MobEffects.NAUSEA);
        }
    }
}

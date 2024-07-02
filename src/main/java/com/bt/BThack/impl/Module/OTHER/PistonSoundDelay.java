package com.bt.BThack.impl.Module.OTHER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class PistonSoundDelay extends Module {

    private long delay = 0;

    public PistonSoundDelay() {
        super("PistonSoundDelay",
                "Reduces annoying pistons sounds when used near a lag machine.",
                Keyboard.KEY_NONE,
                Category.OTHER,
                false
        );

        addSlider("Sound Delay", this, 5, 1,15,true);
    }

    @SubscribeEvent
    public void onSound(PacketEvent.Receive e) {
        if (nullCheck()) return;
        if (e.getPacket() instanceof SPacketSoundEffect) {
            SPacketSoundEffect sPacketSoundEffect = (SPacketSoundEffect) e.getPacket();
            if (sPacketSoundEffect.getSound() == SoundEvents.BLOCK_PISTON_EXTEND || sPacketSoundEffect.getSound() == SoundEvents.BLOCK_PISTON_CONTRACT) {
                if (delay < getSlider(this.name, "Sound Delay")) {
                    delay++;
                    e.setCanceled(true);
                } else {
                    delay = 0;
                }
            }
        }
    }
}

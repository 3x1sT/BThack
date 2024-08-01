package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class IceSpeed extends Module {
    public IceSpeed() {
        super("IceSpeed",
                "Makes you faster on the ice.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addSlider("Ice Speed", this, 0.4, 0.5, 0.3 , false);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        arrayListInfo = "" + getSlider(this.name, "Ice Speed");

        float speed = (float) getSlider(this.name, "Ice Speed");

        Blocks.ICE.setDefaultSlipperiness(speed);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(speed);
        Blocks.PACKED_ICE.setDefaultSlipperiness(speed);
    }

    @Override
    public void onDisable() {
        Blocks.ICE.setDefaultSlipperiness(0.98f);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(0.98f);
        Blocks.PACKED_ICE.setDefaultSlipperiness(0.98f);
    }
}

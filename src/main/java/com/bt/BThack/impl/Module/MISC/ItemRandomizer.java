package com.bt.BThack.impl.Module.MISC;

import com.bt.BTbot.api.Utils.Generate.GenerateNumber;
import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ItemRandomizer extends Module {

    public ItemRandomizer() {
        super("ItemRandomizer",
                "While the module is enabled, it randomly changes the current slot in the hotbar.",
                Keyboard.KEY_NONE,
                Category.MISC,
                false
        );
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        mc.player.inventory.currentItem = GenerateNumber.generateInt(0, 8);
        mc.playerController.updateController();
    }
}

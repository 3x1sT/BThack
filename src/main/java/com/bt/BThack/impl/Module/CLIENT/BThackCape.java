package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class BThackCape extends Module {
    public static final ResourceLocation BThack_Cape = new ResourceLocation("bthack", "bthack_cape.png");

    public BThackCape() {
        super("BThackCape",
                "Replaces the player's cape and elites with the cape and elites from the BThack client.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                true
        );

        allowRemapKeyCode = false;
        visible = false;
    }
}

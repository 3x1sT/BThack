package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.world.GameType;
import org.lwjgl.input.Keyboard;

public class FakeCreative extends Module {
    public GameType oldType;

    public FakeCreative() {
        super("FakeCreative",
                "Simulates creative mode.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        oldType = mc.playerController.getCurrentGameType();
        if (mc.player != null) {
            mc.playerController.setGameType(GameType.CREATIVE);
        } else toggle();
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        if (mc.player != null) {
            if (oldType == GameType.SURVIVAL)
                mc.playerController.setGameType(GameType.SURVIVAL);
            if (oldType == GameType.SPECTATOR)
                mc.playerController.setGameType(GameType.SPECTATOR);
            if (oldType == GameType.ADVENTURE)
                mc.playerController.setGameType(GameType.ADVENTURE);
            if (oldType == GameType.CREATIVE)
                mc.playerController.setGameType(GameType.CREATIVE);
        }
    }
}

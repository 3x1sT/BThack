package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;

public class FakePlayer extends Module {
    EntityOtherPlayerMP fakePlayer;
    public FakePlayer() {
        super("FakePlayer",
                "Creates a fake player.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        if (mc.player != null) {
            fakePlayer = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
            fakePlayer.setEntityId(-1882);
            fakePlayer.copyLocationAndAnglesFrom(mc.player);
            fakePlayer.inventory = mc.player.inventory;
            fakePlayer.rotationYawHead = mc.player.rotationYawHead;
            fakePlayer.setHealth(mc.player.getHealth());
            fakePlayer.capabilities = mc.player.capabilities;
            fakePlayer.experienceLevel = mc.player.experienceLevel;
            fakePlayer.setScore(mc.player.getScore());
            mc.world.addEntityToWorld(fakePlayer.getEntityId(), fakePlayer);
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) {
            return;
        }

        mc.world.removeEntityFromWorld(fakePlayer.getEntityId());
    }
}

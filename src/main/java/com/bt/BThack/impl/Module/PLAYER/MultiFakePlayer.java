package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class MultiFakePlayer extends Module {
    public MultiFakePlayer() {
        super("MultiFakePlayer",
                "Works like a normal FakePlayer, but you can use it to create multiple players at once.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addKeyCode("Summon Key", this);
    }

    ArrayList<EntityOtherPlayerMP> fakePlayers = new ArrayList<>();

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent e) {
        if (nullCheck()) return;

        if (isKeyCodePressed(this.name, "Summon Key")) {
            EntityOtherPlayerMP fakePlayer;

            fakePlayer = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
            fakePlayer.setEntityId(-1882 - fakePlayers.size());
            fakePlayer.copyLocationAndAnglesFrom(mc.player);
            fakePlayer.inventory = mc.player.inventory;
            fakePlayer.rotationYawHead = mc.player.rotationYawHead;
            fakePlayer.setHealth(mc.player.getHealth());
            fakePlayer.capabilities = mc.player.capabilities;
            fakePlayer.experienceLevel = mc.player.experienceLevel;
            fakePlayer.setScore(mc.player.getScore());
            mc.world.addEntityToWorld(fakePlayer.getEntityId(), fakePlayer);
            fakePlayers.add(fakePlayer);
        }
    }

    @Override
    public void onDisable() {
        for (EntityOtherPlayerMP fakePlayer : fakePlayers) {
            mc.world.removeEntityFromWorld(fakePlayer.getEntityId());
        }

        fakePlayers.clear();
    }
}

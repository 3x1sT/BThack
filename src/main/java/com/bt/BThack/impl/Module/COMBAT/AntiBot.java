package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AntiBot extends Module {

    public static List<Entity> bots = new ArrayList<>();
    public AntiBot() {
        super("AntiBot",
                "Prohibits attacks on entities created by anti-cheat.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (!player.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(StandardCharsets.UTF_8))) && player instanceof EntityOtherPlayerMP && !bots.contains(player)) {
                bots.add(player);
            }

            if (!player.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(StandardCharsets.UTF_8))) && player.isInvisible() && player instanceof EntityOtherPlayerMP) {
                mc.world.removeEntity(player);
            }
        }
    }

    public void onDisable() {
        bots.clear();
    }
}

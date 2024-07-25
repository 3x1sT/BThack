package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Clans.Clan;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Social.Enemies.EnemiesUtils;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.Utils.Modules.KillAuraUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Tracers extends Module {

    public Tracers() {
        super("Tracers",
                "Renders tracers leading to entity.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addCheckbox("Players", this, true);
        addCheckbox("Mobs", this, true);
        addCheckbox("Animals", this, true);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        if (nullCheck()) return;

        if (getCheckbox(this.name, "Players")) {
            for (EntityPlayer playerEntity : mc.world.playerEntities) {
                if (playerEntity != null && playerEntity != mc.player && !playerEntity.isDead) {
                    String name = playerEntity.getDisplayNameString();
                    if (FriendsUtils.isFriend(name)) {
                        RenderUtils.trace(playerEntity, mc.getRenderPartialTicks(), 0.03f, 0.96f, 0.86, 1);
                    } else if (EnemiesUtils.isEnemy(name)) {
                        RenderUtils.trace(playerEntity, mc.getRenderPartialTicks(), 1, 0, 0, 1);
                    } else if (ClansUtils.isAlly(name)) {
                        Clan clan = ClansUtils.getFirstClanFromMember(name);

                        if (clan != null) {
                            RenderUtils.trace(playerEntity, mc.getRenderPartialTicks(), clan.getR(), clan.getG(), clan.getB(), 1);
                        } else {
                            RenderUtils.trace(playerEntity, mc.getRenderPartialTicks(), 1, 1, 1, 1);
                        }
                    } else {
                        RenderUtils.trace(playerEntity, mc.getRenderPartialTicks(), 1, 1, 1, 1);
                    }
                }
            }
        }

        boolean mobs = getCheckbox(this.name, "Mobs");
        boolean animals = getCheckbox(this.name, "Animals");

        if (mobs || animals) {
            for (Entity entity : mc.world.loadedEntityList) {
                if (mobs && KillAuraUtils.isCurrentMonster(entity)) {
                    RenderUtils.trace(entity, mc.getRenderPartialTicks(), 0.83F,0.92F,0.17F, 1);
                }
                if (animals && KillAuraUtils.isCurrentAnimal(entity)) {
                    RenderUtils.trace(entity, mc.getRenderPartialTicks(), 0.69F,1,0.34F, 1);
                }
            }
        }
    }
}

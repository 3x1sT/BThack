package com.bt.BThack.api.Utils.Modules;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Clans.Clan;
import com.bt.BThack.api.Social.Clans.ClanStatus;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.Comparator;

public class KillAuraUtils implements Mc {
    public static void CoolDownAttack(Entity target) {
        if (mc.player.getCooledAttackStrength(0) == 1) {
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    public static void SpamAttack(Entity target) {
        mc.playerController.attackEntity(mc.player, target);
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.player.resetCooldown();
    }

    public static void DelayAttack(Entity target) {
        mc.playerController.attackEntity(mc.player, target);
        mc.player.swingArm(EnumHand.MAIN_HAND);
    }

    public static EntityPlayer filterPlayers(String mode) {

        double range = Module.getSlider(mode, "Range");

        return mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer != mc.player && !isFriend(entityPlayer, mode) && !isTeammate(entityPlayer,mode) && isSuccessfulClanMember(entityPlayer, mode) && entityPlayer.isEntityAlive()).min(Comparator.comparing(entityPlayer ->
                entityPlayer.getDistance(mc.player))).filter(entityPlayer -> entityPlayer.getDistance(mc.player) <= range).orElse(null);
    }

    public static Entity filterEntity(double range) {
        return mc.world.loadedEntityList.stream().filter(entity1 -> entity1 != mc.player && entity1.isEntityAlive()).min(Comparator.comparing(entity1 ->
                entity1.getDistance(mc.player))).filter(entity1 -> entity1.getDistance(mc.player) <= range).orElse(null);
    }

    public static boolean canBeSeeTarget(String mode, Entity target) {
        if (!Module.getCheckbox(mode, "Ignore Walls")) {
            return mc.player.canEntityBeSeen(target);
        } else {
            return true;
        }
    }

    public static boolean isCurrentMob(Entity entity) {
        return entity instanceof EntityMob || entity instanceof EntityGolem || entity instanceof EntityAnimal || entity instanceof EntityWaterMob;
    }

    public static boolean isCurrentMonster(Entity entity) {
        return entity instanceof EntityMob || entity instanceof EntityGolem;
    }

    public static boolean isCurrentAnimal(Entity entity) {
        return entity instanceof EntityAgeable;
    }

    private static boolean isFriend(EntityPlayer player, String mode) {
        if (!Module.getCheckbox(mode, "Friends")) {
            return FriendsUtils.isFriend(player);
        }
        return false;
    }

    private static boolean isTeammate(EntityPlayer player, String mode) {
        if (!Module.getCheckbox(mode, "Teammates")) {
            return mc.player.isOnSameTeam(player);
        }
        return false;
    }

    public static boolean isSuccessfulClanMember(EntityPlayer player, String mode) {
        if (ClansUtils.modulesWithClanManager.contains(mode)) {
            if (Module.getCheckbox(mode, "Clan Manager")) {
                ArrayList<Clan> clans = ClansUtils.getClansFromMember(player.getDisplayNameString());
                switch (Module.getMode(mode, "Clan Mode")) {
                    case "Only Enemy":
                        if (!clans.isEmpty()) {
                            for (Clan clan : clans) {
                                if (ClanStatus.ENEMY.getName().contains(clan.getStatus())) {
                                    return true;
                                }
                            }
                            return false;
                        } else {
                            return true;
                        }
                    case "Neutral Also":
                        if (!clans.isEmpty()) {
                            for (Clan clan : clans) {
                                if (ClanStatus.ENEMY.getName().contains(clan.getStatus()) || ClanStatus.NEUTRAL.getName().contains(clan.getStatus())) {
                                    return true;
                                }
                            }
                            return false;
                        } else {
                            return true;
                        }
                    case "Target Clan":
                        if (!clans.isEmpty()) {
                            for (Clan clan : clans) {
                                if (clan.getName().equals(Module.getMode(mode, "Target"))) {
                                    return true;
                                }
                            }
                            return false;
                        } else {
                            return true;
                        }
                    case "All Clans":
                        return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }
}

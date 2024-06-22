package com.bt.BThack.api.Utils.Modules;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Allies.AlliesUtils;
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

        return mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer != mc.player && !isFriend(entityPlayer, mode) && !isTeammate(entityPlayer,mode) && !isAlly(entityPlayer, mode)).min(Comparator.comparing(entityPlayer ->
                entityPlayer.getDistance(mc.player))).filter(entityPlayer -> entityPlayer.getDistance(mc.player) <= range).orElse(null);
    }

    public static Entity filterEntity(double range) {
        return mc.world.loadedEntityList.stream().filter(entity1 -> entity1 != mc.player).min(Comparator.comparing(entity1 ->
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

    private static boolean isAlly(EntityPlayer player, String mode) {
        if (!Module.getCheckbox(mode, "Allies")) {
            return AlliesUtils.isAlly(player);
        }
        return false;
    }
}

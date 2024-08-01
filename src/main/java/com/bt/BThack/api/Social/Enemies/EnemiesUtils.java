package com.bt.BThack.api.Social.Enemies;

import com.bt.BThack.BThack;
import com.bt.BThack.api.Social.SocialUtils;
import net.minecraft.entity.player.EntityPlayer;

public class EnemiesUtils {
    public static boolean correctAdd = false;
    public static boolean correctRemove = false;

    public static void saveEnemies() {
        SocialUtils.save("Enemies/Enemies.txt", EnemyList.Enemies);
    }

    public static void addEnemy(String enemy) {
        EnemyList.loadEnemies();
        correctAdd = false;
        if (!EnemyList.Enemies.contains(enemy)) {
            EnemyList.Enemies.add(enemy);
            saveEnemies();
            correctAdd = true;
        } else {
            BThack.log("This enemy already exists!");
        }

    }

    public static void removeEnemy(String enemy) {
        EnemyList.loadEnemies();
        correctRemove = false;
        if (EnemyList.Enemies.contains(enemy)) {
            EnemyList.Enemies.remove(enemy);
            saveEnemies();
            correctRemove = true;
        } else {
            BThack.log("This enemy doesn't exist!");
        }

    }

    public static boolean isEnemy(EntityPlayer player) {
        String name = player.getDisplayNameString();
        return EnemyList.Enemies.contains(name);
    }
    public static boolean isEnemy(String name) {
        return EnemyList.Enemies.contains(name);
    }
}

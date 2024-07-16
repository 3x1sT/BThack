package com.bt.BThack.api.Social.Enemies;

import com.bt.BThack.api.Social.SocialUtils;

import java.util.ArrayList;

public class EnemyList {
    public static ArrayList<String> Enemies = new ArrayList<>();

    public static void loadEnemies() {
        SocialUtils.load("Enemies/Enemies.txt", Enemies);
    }
}

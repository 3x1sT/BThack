package com.bt.BThack.api.Social.Allies;

import com.bt.BThack.BThack;
import com.bt.BThack.ConfigInit;
import net.minecraft.entity.player.EntityPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AlliesUtils {
    public static ArrayList<Ally> allies = new ArrayList<>();

    public static boolean addAlly(String name, String clanName, float r, float g, float b) {
        ArrayList<String> allyNames = new ArrayList<>();
        for (Ally ally : allies) {
            allyNames.add(ally.getName());
        }

        if (!allyNames.contains(name)) {
            allies.add(new Ally(name, clanName, r,g,b));
            try {
                ConfigInit.saveAllies();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            BThack.log("This ally already exists!");
        }
        return false;
    }

    public static boolean addAlly(String name, String clanName, String color) {
        ArrayList<String> allyNames = new ArrayList<>();
        for (Ally ally : allies) {
            allyNames.add(ally.getName());
        }

        if (!allyNames.contains(name)) {
            allies.add(new Ally(name, clanName, color));
            try {
                ConfigInit.saveAllies();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            BThack.log("This ally already exists!");
        }
        return false;
    }

    public static boolean removeAlly(String name) {
        for (Ally ally : allies) {
            if (ally.getName().equals(name)) {
                allies.remove(ally);
                Path path = Paths.get("BThack/Social/Allies/" + ally.getName() + ".json");
                if (Files.exists(path)) {
                    File file = new File(path.toUri());
                    file.delete();
                }
                try {
                    ConfigInit.saveAllies();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            } else {
                BThack.log("This ally doesn't exist!");
            }
        }
        return false;
    }

    public static void reloadAllies() {
        allies.clear();
        try {
            ConfigInit.loadAllies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isAlly(EntityPlayer player) {
        String name = player.getDisplayNameString();
        for (Ally ally : allies) {
            if (ally.getName().equals(name)) return true;
        }
        return false;
    }

    public static boolean isAlly(String name) {
        for (Ally ally : allies) {
            if (ally.getName().equals(name)) return true;
        }
        return false;
    }

    public static Ally getAlly(String name) {
        for (Ally ally : allies) {
            if (ally.getName().equals(name)) return ally;
        }
        return null;
    }
}

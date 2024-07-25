package com.bt.BThack.api.Social.Clans;

import com.bt.BThack.BThack;
import com.bt.BThack.System.ConfigInit;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Clans.Allies.Ally;
import net.minecraft.entity.player.EntityPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ClansUtils {
    public static final ArrayList<Clan> clans = new ArrayList<>();

    public static Clan getClan(String clanName) {
        for (Clan clan : clans) {
            if (clan.getName().equals(clanName))
                return clan;
        }

        return null;
    }


    public static Clan getFirstClanFromMember(String memberName) {
        for (Clan clan : clans) {
            for (Ally ally : clan.members) {
                if (ally.getName().equals(memberName)) {
                    return clan;
                }
            }
        }
        return null;
    }

    public static ArrayList<Clan> getClansFromMember(String memberName) {
        ArrayList<Clan> temp = new ArrayList<>();

        for (Clan clan : clans) {
            for (Ally ally : clan.members) {
                if (ally.getName().equals(memberName)) {
                    temp.add(clan);
                }
            }
        }
        return temp;
    }

    public static boolean addClan(String clanName, float r, float g, float b) {
        ArrayList<String> clanNames = new ArrayList<>();

        for (Clan clan : clans) {
            clanNames.add(clan.getName());
        }

        if (!clanNames.contains(clanName)) {
            clans.add(new Clan(clanName, r, g, b));
            try {
                ConfigInit.saveClans();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            BThack.log("This clan already exists!");
        }
        return false;
    }

    public static boolean addClan(String clanName, String color) {
        ArrayList<String> clanNames = new ArrayList<>();

        for (Clan clan : clans) {
            clanNames.add(clan.getName());
        }

        if (!clanNames.contains(clanName)) {
            clans.add(new Clan(clanName, color));
            try {
                ConfigInit.saveClans();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            BThack.log("This clan already exists!");
        }
        return false;
    }

    public static boolean removeClan(String clanName) {
        for (Clan clan : clans) {
            if (clan.getName().equals(clanName)) {
                clans.remove(clan);
                Path path = Paths.get("BThack/Social/Clans/" + clan.getName() + ".json");
                if (Files.exists(path)) {
                    File file = new File(path.toUri());
                    file.delete();
                }
                try {
                    ConfigInit.saveClans();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }
        BThack.log("This clan doesn't exist!");
        return false;
    }

    public static void reloadClans() {
        clans.clear();
        try {
            ConfigInit.loadClans();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isAlly(EntityPlayer player) {
        String name = player.getDisplayNameString();
        return isAlly(name);
    }

    public static boolean isAlly(String name) {
        for (Clan clan : clans) {
            for (Ally ally : clan.members) {
                if (ally.getName().equals(name))
                    return true;
            }
        }
        return false;
    }

    public static boolean isClanMember(String targetClanName, String memberName) {
        Clan clan = getClan(targetClanName);

        if (clan == null) return false;

        for (Ally ally : clan.members) {
            if (ally.getName().equals(memberName)) {
                return true;
            }
        }
        return false;
    }

    public static ClanStatus getClanStatus(String clanName) {
        for (Clan clan : clans) {
            if (clan.getName().equals(clanName)) {
                switch (clan.getStatus()) {
                    case "Friendly":
                        return ClanStatus.FRIENDLY;
                    case "Neutral":
                    default:
                        return ClanStatus.NEUTRAL;
                    case "Enemy":
                        return ClanStatus.ENEMY;
                }
            }
        }
        return null;
    }


    public static final ArrayList<String> modulesWithClanManager = new ArrayList<>();

    public static void addClanManagerInModule(Module module) {
        ArrayList<String> targetMode = new ArrayList<>(Arrays.asList(
                "Only Enemy",
                "Neutral Also",
                "Target Clan",
                "All Clans"
        ));
        ArrayList<String> clanNames = new ArrayList<>();
        for (Clan clan : clans) {
            clanNames.add(clan.getName());
        }
        if (clanNames.isEmpty()) {
            clanNames.add("Null");
        }

        Module.addCheckbox("Clan Manager", module, true);

        Module.addMode("Clan Manager", true, "Clan Mode", module, targetMode);
        Module.addMode("Clan Mode", "Target Clan", "Target", module, clanNames);

        modulesWithClanManager.add(module.getName());
    }

    public static void reloadClanListOnModules() {
        ArrayList<String> clanNames = new ArrayList<>();
        for (Clan clan : clans) {
            clanNames.add(clan.getName());
        }
        if (clanNames.isEmpty()) {
            clanNames.add("Null");
        }
        for (String moduleName : modulesWithClanManager) {
            BThack.instance.settingsManager.getModuleSettingByName(moduleName, "Target").setOptions(clanNames);
            BThack.instance.settingsManager.getModuleSettingByName(moduleName, "Target").setValString(BThack.instance.settingsManager.getModuleSettingByName(moduleName, "Target").getOptions().get(BThack.instance.settingsManager.getModuleSettingByName(moduleName, "Target").getIndex()));
        }
    }
}

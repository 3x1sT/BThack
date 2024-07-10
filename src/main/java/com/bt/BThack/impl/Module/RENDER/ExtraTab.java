package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Allies.AlliesUtils;
import com.bt.BThack.api.Social.Allies.Ally;
import com.bt.BThack.api.Social.Enemies.EnemiesUtils;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.Objects;

/**
 * @see com.bt.BThack.mixins.mixin.MixinGuiPlayerTabOverlay
 */

public class ExtraTab extends Module {
    public ExtraTab() {
        super("ExtraTab",
                "Expands the tab menu.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );
        addCheckbox("Highlight Social", this, true);
        addSlider("Tab Size", this, 265, 80, 400, true);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    public static String getPlayerName(NetworkPlayerInfo info) {
        String name = info.getDisplayName() != null ? info.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(info.getPlayerTeam(), info.getGameProfile().getName());
        Ally ally = AlliesUtils.getAlly(name);
        return AlliesUtils.isAlly(name) ? name + ChatFormatting.RESET + "[" + ChatFormatting.BOLD + Objects.requireNonNull(ally).getClanName() + ChatFormatting.RESET + "]": FriendsUtils.isFriend(name) ? ChatFormatting.GREEN + name : EnemiesUtils.isEnemy(name) ? ChatFormatting.RED + name : name;
    }

    public static List<NetworkPlayerInfo> subList(List<NetworkPlayerInfo> list, List<NetworkPlayerInfo> newList) {
        if (!Client.getModuleByName("ExtraTab").isEnabled()) {
            return newList;
        } else {
            return list.subList(0, Math.min((int)getSlider("ExtraTab", "Tab Size"), list.size()));
        }
    }
}

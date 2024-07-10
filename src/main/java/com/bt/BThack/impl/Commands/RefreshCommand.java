package com.bt.BThack.impl.Commands;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Allies.AlliesUtils;
import com.bt.BThack.api.Social.Enemies.EnemyList;
import com.bt.BThack.api.Social.Friends.FriendList;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

public class RefreshCommand extends AbstractCommand {

    public RefreshCommand() {
        super("Updates friends/enemies/allies list(needed if you changed the list via txt file)", "refresh [friends/enemies/allies]", "refresh"
        );
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0 || !args[0].equals("friends") && !args[0].equals("enemies") && !args[0].equals("allies")) {
            invalidArgumentError();
        } else {
            switch (args[0]) {
                case "friends":
                    FriendList.loadFriends();
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Friends refreshed!");
                    break;
                case "enemies":
                    EnemyList.loadEnemies();
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Enemies refreshed!");
                    break;
                case "allies":
                    AlliesUtils.reloadAllies();
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Allies refreshed!");
                    break;
            }
        }
    }
}

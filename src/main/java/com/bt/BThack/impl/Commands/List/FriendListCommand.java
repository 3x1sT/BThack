package com.bt.BThack.impl.Commands.List;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Friends.FriendList;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;

public class FriendListCommand extends AbstractCommand {

    public FriendListCommand() {
        super("Shows the entire list of friends.", "friendlist", "friendlist"
        );
    }

    @Override
    public void execute(String[] args) {
        ChatUtils.sendMessage(ChatFormatting.AQUA + "All your friends:");

        for (String name : FriendList.Friends) {
            mc.player.sendMessage(new TextComponentString(name));
        }
    }
}

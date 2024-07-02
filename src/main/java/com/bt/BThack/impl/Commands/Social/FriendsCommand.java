package com.bt.BThack.impl.Commands.Social;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

public class FriendsCommand extends AbstractCommand {

    public FriendsCommand() {
        super("Adds/removes a friend", "friends [add/remove] [friendName]", "friends"
        );
    }


    @Override
    public void execute(String[] args) {
        if (args.length != 0 && args.length != 1 && (args[0].equals("add") || args[0].equals("remove"))) {
            if (args[0].equals("add")) {
                FriendsUtils.addFriend(args[1]);
                if (FriendsUtils.correctAdd) {
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Friend successfully added!");
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "This friend already exists!");
                }
            }

            if (args[0].equals("remove")) {
                FriendsUtils.removeFriend(args[1]);
                if (FriendsUtils.correctRemove) {
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Friend successfully removed!");
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "This friend doesn't exist!");
                }
            }

        } else {
            invalidArgumentError();
        }
    }
}

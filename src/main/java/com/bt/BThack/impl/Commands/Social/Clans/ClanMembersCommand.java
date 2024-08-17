package com.bt.BThack.impl.Commands.Social.Clans;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Clans.Clan;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

public class ClanMembersCommand extends AbstractCommand {

    public ClanMembersCommand() {
        super("Adds/removes a clan member.", "clanMember [add / remove] [clanName] [memberName]", "clanMember"
        );
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 2 && (args[0].equals("add") || args[0].equals("remove"))) {
            switch (args[0]) {
                case "add":
                    Clan clan = ClansUtils.getClan(args[1]);
                    if (clan != null) {
                        if (clan.addMemberToClan(args[2])) {
                            ChatUtils.sendMessage(ChatFormatting.AQUA + "Member successfully added!");
                        } else {
                            ChatUtils.sendMessage(ChatFormatting.YELLOW + "The member is already in the clan.");
                        }
                    } else {
                        ChatUtils.sendMessage(ChatFormatting.YELLOW + "There is no such clan. Create it using the $clans add ... command.");
                    }
                    break;
                case "remove":
                    Clan clan2 = ClansUtils.getClan(args[1]);
                    if (clan2 != null) {
                        if (clan2.removeMemberFromClan(args[2])) {
                            ChatUtils.sendMessage(ChatFormatting.AQUA + "Member successfully removed!");
                        } else {
                            ChatUtils.sendMessage(ChatFormatting.YELLOW + "The member is no longer in the clan.");
                        }
                    } else {
                        ChatUtils.sendMessage(ChatFormatting.YELLOW + "There is no such clan. Create it using the $clans add ... command.");
                    }
                    break;
            }
        } else
            invalidArgumentError();
    }
}

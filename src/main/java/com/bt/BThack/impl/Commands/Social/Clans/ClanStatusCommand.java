package com.bt.BThack.impl.Commands.Social.Clans;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Clans.Clan;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

public class ClanStatusCommand extends AbstractCommand {

    public ClanStatusCommand() {
        super("Changes the status of the clan.", "clanStatus [clanName] [Friendly / Neutral / Enemy]", "clanStatus"
        );
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 1) {
            Clan clan = ClansUtils.getClan(args[0]);

            if (clan != null) {
                if (clan.setStatus(args[1])) {
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Clan " + args[0] + " has been successfully set status: " + args[1] +"!");
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "Incorrect clan status! Try the following options: Friendly / Neutral / Enemy");
                }
            } else {
                ChatUtils.sendMessage(ChatFormatting.YELLOW + "There is no such clan. Create it using the $clans add ... command.");
            }
        } else
            invalidArgumentError();
    }
}

package com.bt.BThack.impl.Commands.List;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Clans.Allies.Ally;
import com.bt.BThack.api.Social.Clans.Clan;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;

public class clansListCommand extends AbstractCommand {

    public clansListCommand() {
        super("Shows the entire list of clans, as well as their members.", "clanlist", "clanlist"
        );
    }

    @Override
    public void execute(String[] args) {
        ChatUtils.sendMessage(ChatFormatting.AQUA + "All clans:");

        for (Clan clan : ClansUtils.clans) {
            mc.player.sendMessage(new TextComponentString(clan.getName()));
            mc.player.sendMessage(new TextComponentString("  Members:"));
            for (Ally ally : clan.members) {
                mc.player.sendMessage(new TextComponentString("    " + ally.getName()));
            }
        }
    }
}

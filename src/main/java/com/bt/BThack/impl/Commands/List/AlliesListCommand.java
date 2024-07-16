package com.bt.BThack.impl.Commands.List;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Allies.AlliesUtils;
import com.bt.BThack.api.Social.Allies.Ally;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;

public class AlliesListCommand extends AbstractCommand {

    public AlliesListCommand() {
        super("Shows the entire list of allies.", "allylist", "allylist"
        );
    }

    @Override
    public void execute(String[] args) {
        ChatUtils.sendMessage(ChatFormatting.AQUA + "All your allies:");

        for (Ally ally : AlliesUtils.allies) {
            mc.player.sendMessage(new TextComponentString(ally.getName() + " [" + ally.getClanName() + "]"));
        }
    }
}

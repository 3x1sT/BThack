package com.bt.BThack.impl.Commands.List;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.CommandSystem.command.ICommand;
import com.bt.BThack.api.CommandSystem.manager.CommandManager;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;

public class CommandListCommand extends AbstractCommand {

    public CommandListCommand() {
        super("Lists all existing BThack commands", "commandlist", "commandlist"
        );
    }

    @Override
    public void execute(String[] args) {
        ChatUtils.sendMessage(ChatFormatting.AQUA + "All BThack commands:");

        for (ICommand command : CommandManager.commands) {
            mc.player.sendMessage(new TextComponentString(ChatFormatting.GRAY + "$" + command.getUsage() + ChatFormatting.WHITE +" - " + ChatFormatting.YELLOW + command.getDescription() + ";"));
        }
    }
}

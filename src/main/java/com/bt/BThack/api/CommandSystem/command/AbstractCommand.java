package com.bt.BThack.api.CommandSystem.command;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.mojang.realmsclient.gui.ChatFormatting;

public abstract class AbstractCommand implements ICommand, Mc {
    private final String[] aliases;
    private final String description, usage;

    public AbstractCommand(String description, String usage, String... aliases) {
        this.aliases = aliases;
        this.description = description;
        this.usage = usage;
    }

    @Override public String[] getAliases() { return aliases; }
    @Override public String getDescription() { return description; }
    @Override public String getUsage() { return usage; }

    public void invalidArgumentError() {
        ChatUtils.sendMessage(ChatFormatting.RED + "Invalid command arguments! Please try " + Client.ChatPrefix + this.getUsage());
    }
}
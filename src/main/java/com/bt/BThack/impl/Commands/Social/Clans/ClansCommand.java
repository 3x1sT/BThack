package com.bt.BThack.impl.Commands.Social.Clans;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import org.apache.commons.lang3.math.NumberUtils;

public class ClansCommand extends AbstractCommand {

    public ClansCommand() {
        super("Deletes/Adds a clan.", "clans [ [add] [clanName] [ [red] [green] [blue] ] / [ red/orange/yellow/green/blue/violet ] ] / [ [remove] [clanName] ]", "clans"
        );
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 1 && (args[0].equals("add") || args[0].equals("remove"))) {
            switch (args[0]) {
                case "add":
                    if (args.length > 3) {
                        if (args.length > 4) {
                            if (NumberUtils.isNumber(args[2]) && NumberUtils.isNumber(args[3]) && NumberUtils.isNumber(args[4])) {
                                if (Integer.parseInt(args[2]) < 256 && Integer.parseInt(args[3]) < 256 && Integer.parseInt(args[4]) < 256) {
                                    if (ClansUtils.addClan(args[1], (float) Integer.parseInt(args[2]) / 255, (float) Integer.parseInt(args[3]) / 255, (float) Integer.parseInt(args[4]) / 255)) {
                                        ChatUtils.sendMessage(ChatFormatting.AQUA + "Clan successfully added!");
                                    } else {
                                        ChatUtils.sendMessage(ChatFormatting.YELLOW + "This clan already exists!");
                                    }
                                } else
                                    invalidArgumentError();
                            } else
                                invalidArgumentError();
                        } else
                            invalidArgumentError();
                    } else {
                        if (ClansUtils.addClan(args[1], args[2])) {
                            ChatUtils.sendMessage(ChatFormatting.AQUA + "Clan successfully added!");
                        } else {
                            ChatUtils.sendMessage(ChatFormatting.YELLOW + "This clan already exists!");
                        }
                    }
                    break;
                case "remove":
                    if (ClansUtils.removeClan(args[1])) {
                        ChatUtils.sendMessage(ChatFormatting.AQUA + "Clan successfully removed!");
                    } else {
                        ChatUtils.sendMessage(ChatFormatting.YELLOW + "This clan doesn't exist!");
                    }
                    break;
            }
        } else
            invalidArgumentError();
    }
}

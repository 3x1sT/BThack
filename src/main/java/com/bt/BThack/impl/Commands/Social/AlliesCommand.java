package com.bt.BThack.impl.Commands.Social;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Allies.AlliesUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import org.apache.commons.lang3.math.NumberUtils;

public class AlliesCommand extends AbstractCommand {

    public AlliesCommand() {
        super("Adds/removes the ally.", "allies [ [add] [nickName] [clanName] [ [red] [green] [blue] ] / [ red/orange/yellow/green/blue/violet ] ] / [ [remove] [nickName] ]", "allies"
        );
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 1 && (args[0].equals("add") || args[0].equals("remove"))) {
            switch (args[0]) {
                case "add":
                    if (args.length > 3) {
                        if (NumberUtils.isNumber(args[3])) {
                            if (args.length > 5) {
                                if (Integer.parseInt(args[3]) < 256 && Integer.parseInt(args[4]) < 256 && Integer.parseInt(args[5]) < 256) {
                                    if (AlliesUtils.addAlly(args[1], args[2], (float)Integer.parseInt(args[3]) / 255, (float)Integer.parseInt(args[4]) / 255 , (float)Integer.parseInt(args[5]) / 255)) {
                                        ChatUtils.sendMessage(ChatFormatting.AQUA + "Ally successfully added!");
                                    } else {
                                        ChatUtils.sendMessage(ChatFormatting.YELLOW + "This ally already exists!");
                                    }
                                } else ChatUtils.sendMessage(ChatFormatting.RED + "The r g b values cannot be greater than 255!");
                            } else invalidArgumentError();
                        } else {
                            if (AlliesUtils.addAlly(args[1], args[2], args[3])) {
                                ChatUtils.sendMessage(ChatFormatting.AQUA + "Ally successfully added!");
                            } else {
                                ChatUtils.sendMessage(ChatFormatting.YELLOW + "This ally already exists!");
                            }
                        }
                    } else invalidArgumentError();
                    break;
                case "remove":
                    if (AlliesUtils.removeAlly(args[1])) {
                        ChatUtils.sendMessage(ChatFormatting.AQUA + "Ally successfully removed!");
                    } else {
                        ChatUtils.sendMessage(ChatFormatting.YELLOW + "This ally doesn't exist!");
                    }
                    break;
            }
        } else invalidArgumentError();
    }
}

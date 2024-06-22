package com.bt.BThack.impl.Commands;

import com.bt.BThack.api.Social.Allies.AlliesUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.math.NumberUtils;

@SideOnly(Side.CLIENT)
public class AlliesCommand extends CommandBase implements IClientCommand {

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
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
                            } else error();
                        } else {
                            if (AlliesUtils.addAlly(args[1], args[2], args[3])) {
                                ChatUtils.sendMessage(ChatFormatting.AQUA + "Ally successfully added!");
                            } else {
                                ChatUtils.sendMessage(ChatFormatting.YELLOW + "This ally already exists!");
                            }
                        }
                    } else error();
                    break;
                case "remove":
                    if (AlliesUtils.removeAlly(args[1])) {
                        ChatUtils.sendMessage(ChatFormatting.AQUA + "Ally successfully removed!");
                    } else {
                        ChatUtils.sendMessage(ChatFormatting.YELLOW + "This ally doesn't exist!");
                    }
                    break;
            }
        } else error();
    }

    @MethodsReturnNonnullByDefault
    public String getName() {
        return "allies";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @MethodsReturnNonnullByDefault
    public String getUsage(ICommandSender sender) {
        return "/allies";
    }

    @MethodsReturnNonnullByDefault
    public boolean allowUsageWithoutPrefix(ICommandSender iCommandSender, String s) {
        return false;
    }

    private void error() {
        ChatUtils.sendMessage(ChatFormatting.RED + "Invalid command arguments! Please try /allies <<add> <ally name> <clanName> <red> <green> <blue>> / <<remove> <ally name>>");
    }
}
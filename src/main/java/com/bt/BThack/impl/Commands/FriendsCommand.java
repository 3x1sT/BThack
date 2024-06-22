package com.bt.BThack.impl.Commands;

import com.bt.BThack.api.Social.Friends.FriendsUtils;
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

@SideOnly(Side.CLIENT)
public class FriendsCommand extends CommandBase implements IClientCommand {
    //public static final String NAME = "friends";

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
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
            ChatUtils.sendMessage(ChatFormatting.RED + "Invalid command arguments! Please try /friends <add/remove> <friend nickname>");
        }
    }

    @MethodsReturnNonnullByDefault
    public String getName() {
        return "friends";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @MethodsReturnNonnullByDefault
    public String getUsage(ICommandSender sender) {
        return "/friends";
    }

    @MethodsReturnNonnullByDefault
    public boolean allowUsageWithoutPrefix(ICommandSender iCommandSender, String s) {
        return false;
    }
}

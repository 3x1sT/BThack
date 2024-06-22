package com.bt.BThack.impl.Commands;

import com.bt.BThack.api.Social.Allies.AlliesUtils;
import com.bt.BThack.api.Social.Enemies.EnemyList;
import com.bt.BThack.api.Social.Friends.FriendList;
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
public class RefreshCommand extends CommandBase implements IClientCommand {

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0 || !args[0].equals("friends") && !args[0].equals("enemies") && !args[0].equals("allies")) {
            ChatUtils.sendMessage(ChatFormatting.RED + "Invalid command arguments! Please try /refresh <friends/enemies>");
        } else {
            switch (args[0]) {
                case "friends":
                    FriendList.loadFriends();
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Friends refreshed!");
                    break;
                case "enemies":
                    EnemyList.loadEnemies();
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Enemies refreshed!");
                    break;
                case "allies":
                    AlliesUtils.reloadAllies();
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Allies refreshed!");
                    break;
            }
        }
    }

    @MethodsReturnNonnullByDefault
    public String getName() {
        return "refresh";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @MethodsReturnNonnullByDefault
    public String getUsage(ICommandSender sender) {
        return "/refresh";
    }

    @MethodsReturnNonnullByDefault
    public boolean allowUsageWithoutPrefix(ICommandSender iCommandSender, String s) {
        return false;
    }
}

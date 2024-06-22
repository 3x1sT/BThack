package com.bt.BThack.impl.Commands;

import com.bt.BThack.api.Social.Enemies.EnemiesUtils;
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
public class EnemiesCommand extends CommandBase implements IClientCommand {
    //public static final String NAME = "enemies";

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 0 && args.length != 1 && (args[0].equals("add") || args[0].equals("remove"))) {
            if (args[0].equals("add")) {
                EnemiesUtils.addEnemy(args[1]);
                if (EnemiesUtils.correctAdd) {
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Enemy successfully added!");
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "This enemy already exists!");
                }
            }

            if (args[0].equals("remove")) {
                EnemiesUtils.removeEnemy(args[1]);
                if (EnemiesUtils.correctRemove) {
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Enemy successfully removed!");
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "This enemy doesn't exist!");
                }
            }

        } else {
            ChatUtils.sendMessage(ChatFormatting.RED + "Invalid command arguments! Please try /enemies <add/remove> <enemy nickname>");
        }
    }

    @MethodsReturnNonnullByDefault
    public String getName() {
        return "enemies";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @MethodsReturnNonnullByDefault
    public String getUsage(ICommandSender sender) {
        return "/enemies";
    }

    @MethodsReturnNonnullByDefault
    public boolean allowUsageWithoutPrefix(ICommandSender iCommandSender, String s) {
        return false;
    }
}

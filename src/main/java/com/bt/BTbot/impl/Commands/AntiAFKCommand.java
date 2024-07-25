package com.bt.BTbot.impl.Commands;

import com.bt.BTbot.api.Utils.SendUtils;
import com.bt.BTbot.impl.AntiAFK.Start.StartAntiAFK;
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
public class AntiAFKCommand extends CommandBase implements IClientCommand {
    public static final String NAME = "AntiAFK";

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1 && (args[0].equals("start") || args[0].equals("stop"))) {
            if (args[0].equals("start")) {
                StartAntiAFK.start();
            } else {
                StartAntiAFK.stop();
            }
        } else {
            SendUtils.sendMessage(ChatFormatting.RED + "Invalid command arguments! Please try /AntiAFK <start/stop>");
        }
    }

    @MethodsReturnNonnullByDefault
    public String getName() {
        return "AntiAFK";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @MethodsReturnNonnullByDefault
    public String getUsage(ICommandSender sender) {
        return "/AntiAFK";
    }

    @MethodsReturnNonnullByDefault
    public boolean allowUsageWithoutPrefix(ICommandSender iCommandSender, String s) {
        return false;
    }
}

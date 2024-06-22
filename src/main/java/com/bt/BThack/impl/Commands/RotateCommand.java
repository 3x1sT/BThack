package com.bt.BThack.impl.Commands;

import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.mojang.realmsclient.gui.ChatFormatting;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import org.apache.commons.lang3.math.NumberUtils;

public class RotateCommand extends CommandBase implements IClientCommand, Mc {
    //public static final String NAME = "rotate";

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 2 && NumberUtils.isNumber(args[0]) && NumberUtils.isNumber(args[1])) {
            int yaw = Integer.parseInt(args[0]);
            int pitch = Integer.parseInt(args[1]);

            if (isCorrectRotations(yaw, pitch)) {

                mc.player.rotationYaw = yaw;
                mc.player.rotationPitch = pitch;
            } else {
                error();
            }
        } else {
            error();
        }
    }

    @MethodsReturnNonnullByDefault
    public String getName() {
        return "rotate";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @MethodsReturnNonnullByDefault
    public String getUsage(ICommandSender sender) {
        return "/rotate";
    }

    @MethodsReturnNonnullByDefault
    public boolean allowUsageWithoutPrefix(ICommandSender iCommandSender, String s) {
        return false;
    }

    private boolean isCorrectRotations(int yaw, int pitch) {
        return yaw > -30000 && yaw < 30000 && pitch > -90 && pitch < 90;
    }

    private void error() {
        ChatUtils.sendMessage(ChatFormatting.RED + "Invalid command arguments! Please try /rotate <yaw[-30000; 30000]> <pitch[-90; 90]>");
    }
}

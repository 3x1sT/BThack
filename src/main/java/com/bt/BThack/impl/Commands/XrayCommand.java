package com.bt.BThack.impl.Commands;


import com.bt.BThack.api.Storage.BlocksData;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.impl.Module.RENDER.Xray;
import com.mojang.realmsclient.gui.ChatFormatting;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class XrayCommand extends CommandBase implements IClientCommand, Mc {
    //public static final String NAME = "xray";

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0 || args.length == 1 || !args[0].equals("add") && !args[0].equals("remove")) {
            ChatUtils.sendMessage(ChatFormatting.RED + "Invalid command arguments! Please try /xray <add/remove> <block_name>");
        } else {
            Block block = BlocksData.getNeedBlock(args[1]);
            if (args[0].equals("add")) {
                if (!BlocksData.invalidBlock) {
                    if (!Xray.xrayBlocks.contains(block)) {
                        Xray.xrayBlocks.add(block);
                        mc.renderGlobal.loadRenderers();
                        ChatUtils.sendMessage(ChatFormatting.AQUA + "Block added successfully!");
                    } else {
                        ChatUtils.sendMessage(ChatFormatting.YELLOW + "Block has already been added.");
                    }
                } else {
                    ChatUtils.sendMessage(ChatFormatting.RED + "Invalid block name!");
                }
            } else {
                if (!BlocksData.invalidBlock) {
                    if (Xray.xrayBlocks.contains(block)) {
                        Xray.xrayBlocks.remove(block);
                        mc.renderGlobal.loadRenderers();
                        ChatUtils.sendMessage(ChatFormatting.AQUA + "Block removed successfully!");
                    } else {
                        ChatUtils.sendMessage(ChatFormatting.YELLOW + "Block has already been removed.");
                    }
                } else {
                    ChatUtils.sendMessage(ChatFormatting.RED + "Invalid block name!");
                }
            }
        }
    }

    @MethodsReturnNonnullByDefault
    public String getName() {
        return "xray";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @MethodsReturnNonnullByDefault
    public String getUsage(ICommandSender sender) {
        return "/xray";
    }

    @MethodsReturnNonnullByDefault
    public boolean allowUsageWithoutPrefix(ICommandSender iCommandSender, String s) {
        return false;
    }
}

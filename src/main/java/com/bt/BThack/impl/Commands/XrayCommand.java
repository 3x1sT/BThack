package com.bt.BThack.impl.Commands;


import com.bt.BThack.System.Client;
import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Storage.BlocksData;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.impl.Module.RENDER.Xray;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;

public class XrayCommand extends AbstractCommand {

    public XrayCommand() {
        super("Adds/removes a block from the xray list", "xray [add/remove] [block_name]", "xray"
        );
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0 || args.length == 1 || !args[0].equals("add") && !args[0].equals("remove")) {
            invalidArgumentError();
        } else {
            Block block = BlocksData.getNeedBlock(args[1]);
            if (args[0].equals("add")) {
                if (!BlocksData.invalidBlock) {
                    if (!Xray.xrayBlocks.contains(block)) {
                        Xray.xrayBlocks.add(block);
                        if (Client.getModuleByName("Xray").isEnabled())
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
                        if (Client.getModuleByName("Xray").isEnabled())
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
}

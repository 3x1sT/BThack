package com.bt.BThack.impl.Commands;


import com.bt.BThack.System.Client;
import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Utils.BlockUtil;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.impl.Module.RENDER.Xray;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

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
            ItemBlock item = (ItemBlock) BlockUtil.getItemFromName(args[1]);
            if (item == null) {
                ChatUtils.sendMessage(ChatFormatting.RED + "Invalid block name!");
                return;
            }
            Block block = item.getBlock();

            if (args[0].equals("add")) {
                if (!Xray.xrayBlocks.contains(block)) {
                    Xray.xrayBlocks.add(block);
                    if (Client.getModuleByName("Xray").isEnabled())
                        mc.renderGlobal.loadRenderers();
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Block added successfully!");
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "Block has already been added.");
                }
            } else {
                if (Xray.xrayBlocks.contains(block)) {
                    Xray.xrayBlocks.remove(block);
                    if (Client.getModuleByName("Xray").isEnabled())
                        mc.renderGlobal.loadRenderers();
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Block removed successfully!");
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "Block has already been removed.");
                }
            }
        }
    }
}

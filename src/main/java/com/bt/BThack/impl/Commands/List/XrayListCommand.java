package com.bt.BThack.impl.Commands.List;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.impl.Module.RENDER.Xray;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.util.text.TextComponentString;

public class XrayListCommand extends AbstractCommand {

    public XrayListCommand() {
        super("Shows the entire list of xray blocks.", "xraylist", "xraylist"
        );
    }

    @Override
    public void execute(String[] args) {
        ChatUtils.sendMessage(ChatFormatting.AQUA + "All your xray blocks:");

        for (Block block : Xray.xrayBlocks) {
            mc.player.sendMessage(new TextComponentString(block.getLocalizedName()));
        }
    }
}

package com.bt.BThack.impl.Commands;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Utils.BlockUtil;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.impl.Module.PLAYER.ChestPutter;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.item.Item;

public class ChestPutterCommand extends AbstractCommand {

    public ChestPutterCommand() {
        super("Add or remove an item from the ChestPutter list.", "chestputter [add/remove] [item name]", "chestputter"
        );
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            invalidArgumentError();
            return;
        }
        Item item = BlockUtil.getItemFromName(args[1]);
        if (item == null) {
            ChatUtils.sendMessage(ChatFormatting.YELLOW + "This item doesn't exist.");
            return;
        }
        switch (args[0]) {
            case "add":
                if (ChestPutter.putItems.contains(item)) {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "This item already exist in ChestPutter list.");
                    return;
                } else {
                    ChestPutter.putItems.add(item);
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Item added to ChestPutter list!");
                }
                break;
            case "remove":
                boolean removed = false;
                for (Item item1 : ChestPutter.putItems) {
                    if (item1.getRegistryName() == item.getRegistryName()) {
                        ChestPutter.putItems.remove(item1);
                        removed = true;
                    }
                }
                if (removed) {
                    ChatUtils.sendMessage(ChatFormatting.AQUA + "Item removed from ChestPutter list!");
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "This item doesn't exist in ChestPutter list.");
                }
                break;
            default:
                invalidArgumentError();
        }
    }
}

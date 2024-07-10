package com.bt.BThack.impl.Commands.List;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Enemies.EnemyList;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;

public class EnemiesListCommand extends AbstractCommand {

    public EnemiesListCommand() {
        super("Shows the entire list of enemies.", "enemylist", "enemylist"
        );
    }

    @Override
    public void execute(String[] args) {
        ChatUtils.sendMessage(ChatFormatting.AQUA + "All your enemies:");

        for (String name : EnemyList.Enemies) {
            mc.player.sendMessage(new TextComponentString(name));
        }
    }
}

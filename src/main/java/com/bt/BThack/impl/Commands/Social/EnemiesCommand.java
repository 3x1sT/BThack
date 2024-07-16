package com.bt.BThack.impl.Commands.Social;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.api.Social.Enemies.EnemiesUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

public class EnemiesCommand extends AbstractCommand {

    public EnemiesCommand() {
        super("Adds/removes an enemy", "enemies [add/remove] [enemyName]", "enemies"
        );
    }

    @Override
    public void execute(String[] args) {
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
            invalidArgumentError();
        }
    }
}

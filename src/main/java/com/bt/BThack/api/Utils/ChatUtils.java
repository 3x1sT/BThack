package com.bt.BThack.api.Utils;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;

public class ChatUtils implements Mc {
    private static final String prefix = "[" + ChatFormatting.BLUE + Client.cName + ChatFormatting.RESET + "] ";

    public static void sendMessage(String msg) {
        if (mc.player == null) return;
        mc.player.sendMessage(new TextComponentString(prefix + msg));
    }

    public static void sendMessage(String msg, SoundEvent soundEvent) {
        if (mc.player == null) return;
        mc.player.sendMessage(new TextComponentString(prefix + msg));
        mc.player.playSound(soundEvent,1,1);
    }
}

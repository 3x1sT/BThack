package com.bt.BTbot.api.Utils;

import com.bt.BTbot.BTbot;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class SendUtils {
    private static final String prefix = "[" + ChatFormatting.BLUE + BTbot.ModName + ChatFormatting.RESET + "] ";

    public static void sendMessage(String msg) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(prefix + msg));
    }
}

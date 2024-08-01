package com.bt.BThack.api.Utils;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.bt.BThack.BThack;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;

import java.util.Objects;



public final class DiscordUtil implements Mc {
    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();

    private static String details;
    private static String state;

    public static void startup() {
        BThack.log("Discord RPC is starting up!");

        DiscordEventHandlers handlers = new DiscordEventHandlers();

        discordRPC.Discord_Initialize(BThack.APP_ID, handlers, true, "");

        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.largeImageKey = "bthack_icon";
        discordRichPresence.largeImageText = "BThack client";

        discordRPC.Discord_UpdatePresence(discordRichPresence);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    details = "Version " + BThack.VERSION;
                    state = "Main Menu";

                    if (mc.isIntegratedServerRunning()) {
                        state = "Singleplayer | " + Objects.requireNonNull(mc.getIntegratedServer()).getWorldName();
                    } else if (mc.currentScreen instanceof GuiMultiplayer) {
                        state = "Multiplayer Menu";
                    } else if (mc.currentScreen instanceof GuiWorldSelection) {
                        state = "Singleplayer Menu";
                    } else if (mc.getCurrentServerData() != null) {
                        state = "Server | " + mc.getCurrentServerData().serverIP.toLowerCase();
                    }

                    discordRichPresence.details = details;
                    discordRichPresence.state = state;

                    discordRPC.Discord_UpdatePresence(discordRichPresence);
                } catch (Exception exception) {
                    exception.printStackTrace();
                } try {
                    Thread.sleep(5000L);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void shutdown() {
        BThack.log("Discord RPC is shutting down!");

        discordRPC.Discord_Shutdown();
        discordRPC.Discord_ClearPresence();
    }
}

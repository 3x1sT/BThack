package com.bt.BThack.api.Utils;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.bt.BTbot.api.Utils.Generate.GenerateNumber;
import com.bt.BThack.BThack;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.SoundSystem.TinySound;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;

import java.util.Objects;



public final class DiscordUtil implements Mc {
    private static DiscordRPC discordRPC;
    private static DiscordRichPresence discordRichPresence;

    private static String details;
    private static String state;

    private static boolean inited = false;

    public static void init() {
        if (TinySound.isInitialized()) {
            try {
                discordRPC = DiscordRPC.INSTANCE;
                discordRichPresence = new DiscordRichPresence();
                inited = true;
            } catch (Exception e) {
                inited = false;
            }
        }
    }

    public static void startup() {
        if (inited) {
            BThack.log("Discord RPC is starting up!");

            DiscordEventHandlers handlers = new DiscordEventHandlers();

            discordRPC.Discord_Initialize(BThack.APP_ID, handlers, true, "");

            String imageKey = "bthack_icon";

            if (Module.getCheckbox("DiscordRPC", "Secret :3")) {
                int percent = GenerateNumber.generateInt(1, 100);
                if (percent > 0 && percent <= 10) {
                    imageKey = "hentai_face1";
                } else if (percent > 10 && percent <= 20) {
                    imageKey = "hentai_face2";
                } else if (percent > 20 && percent <= 30) {
                    imageKey = "hentai_face3";
                }
            }

            discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
            discordRichPresence.largeImageKey = imageKey;
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
                    }
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }, "RPC-Callback-Handler").start();
        }
    }

    public static void shutdown() {
        if (inited) {
            BThack.log("Discord RPC is shutting down!");

            discordRPC.Discord_Shutdown();
            discordRPC.Discord_ClearPresence();
        }
    }
}

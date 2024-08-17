package com.bt.BThack.impl.Module.MISC;

import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Enemies.EnemiesUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EnemyRadar extends Module {

    protected static boolean pause = false;

    public EnemyRadar() {
        super("EnemyRadar",
                "Alerts you if there is an enemy nearby.",
                Keyboard.KEY_NONE,
                Category.MISC,
                false
        );

        addCheckbox("AutoDisconnect", this, false);
        addSlider("AutoDisconnect", true,"Shutdown Delay", this, 3, 1, 10, true);
    }

    private static final ArrayList<EntityPlayer> reportedEnemies = new ArrayList<>();

    @SubscribeEvent
    public void onWorldRender(TickEvent.ClientTickEvent e) {
        if (nullCheck() || pause) return;

        arrayListInfo = getCheckbox(this.name, "AutoDisconnect") ? "AutoDisconnect" : "";

        for (EntityPlayer player : mc.world.playerEntities) {
            if (EnemiesUtils.isEnemy(player) && !reportedEnemies.contains(player) && player != mc.player) {

                String pattern = "#0.0";
                String health = new DecimalFormat(pattern).format(player.getHealth());

                ChatUtils.sendMessage(ChatFormatting.GOLD + "The enemy has been spotted! " + ChatFormatting.AQUA + "Name: " + ChatFormatting.WHITE + player.getDisplayNameString() + "  " + ChatFormatting.AQUA + "Health: " + ChatFormatting.WHITE + health + "  " + ChatFormatting.AQUA + "Direction: " + ChatFormatting.WHITE + AimBotUtils.getDirection(player));

                reportedEnemies.add(player);

                if (getCheckbox(this.name, "AutoDisconnect")) {
                    ThreadManager.startNewThread(thread -> {

                        int delay = (int)Module.getSlider("EnemyRadar", "Shutdown Delay");

                        ChatUtils.sendMessage(ChatFormatting.RED + "The countdown to disconnecting begins!");

                        while (delay != 0) {
                            ChatUtils.sendMessage(ChatFormatting.RED + "Disconnect after " + delay + "s.");

                            try {
                                thread.sleep(1000);
                            } catch (InterruptedException ignored) {}

                            delay--;
                        }

                        mc.player.connection.handleDisconnect(new SPacketDisconnect());

                        pause = false;
                    });
                    pause = true;
                }
            }
        }

        reportedEnemies.removeIf(player -> !mc.world.playerEntities.contains(player));
    }
}

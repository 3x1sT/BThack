package com.bt.BThack.impl.Module.OTHER.EnemyRadar;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Enemies.EnemiesUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EnemyRadar extends Module {

    protected static boolean pause = false;

    public EnemyRadar() {
        super("EnemyRadar",
                "Alerts you if there is an enemy nearby.",
                Keyboard.KEY_NONE,
                Category.OTHER,
                false
        );

        addCheckbox("AutoDisconnect", this, false);
        addSlider("Shutdown Delay", this, 3, 1, 10, true);
    }

    private static final ArrayList<EntityPlayer> reportedEnemies = new ArrayList<>();

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent e) {
        if (nullCheck() || pause) return;

        for (EntityPlayer player : mc.world.playerEntities) {
            if (EnemiesUtils.isEnemy(player) && !reportedEnemies.contains(player) && player != mc.player) {

                String pattern = "#0.0";
                String health = new DecimalFormat(pattern).format(player.getHealth());

                ChatUtils.sendMessage(ChatFormatting.GOLD + "The enemy has been spotted! " + ChatFormatting.AQUA + "Name: " + ChatFormatting.WHITE + player.getDisplayNameString() + "  " + ChatFormatting.AQUA + "Health: " + ChatFormatting.WHITE + health + "  " + ChatFormatting.AQUA + "Direction: " + ChatFormatting.WHITE + AimBotUtils.getDirection(player));

                reportedEnemies.add(player);

                if (getCheckbox(this.name, "AutoDisconnect")) {
                    new EnemyRadarDisconnectThread().start();
                    pause = true;
                }
            }
        }

        reportedEnemies.removeIf(player -> !mc.world.playerEntities.contains(player));
    }
}

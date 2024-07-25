package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Introvert extends Module {

    public Introvert() {
        super("Introvert",
                "You disconnect if a player is detected nearby.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addSlider("Range", this, 7, 4, 15, false);

        addCheckbox("Friends", this, false);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        arrayListInfo = "" + getSlider(this.name, "Range");

        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == mc.player) continue;

            if (player.getDistance(mc.player) < (float) getSlider(this.name, "Range")) {
                if (getCheckbox(this.name, "Friends")) {
                    mc.player.connection.handleDisconnect(new SPacketDisconnect(new TextComponentString("[Introvert] You were disconnected because a player was detected near you.")));
                } else {
                    if (!FriendsUtils.isFriend(player)) {
                        mc.player.connection.handleDisconnect(new SPacketDisconnect(new TextComponentString("[Introvert] You were disconnected because a player was detected near you.")));
                    }
                }
            }
        }
    }
}

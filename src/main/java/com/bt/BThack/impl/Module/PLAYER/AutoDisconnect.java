package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoDisconnect extends Module {
    public AutoDisconnect() {
        super("AutoDisconnect",
                "Disconnect a player if his health is below the limit.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addSlider("Min Health", this, 10,1,20,true);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        float health = mc.player.getHealth();
        if (health <= getSlider(this.name, "Min Health")) {
            mc.player.connection.handleDisconnect(new SPacketDisconnect(new TextComponentString("You have been disconnected because your HP(" + health + ") has reached the minimum limit(" + getSlider(this.name, "Min Health") + ").")));
        }
    }
}

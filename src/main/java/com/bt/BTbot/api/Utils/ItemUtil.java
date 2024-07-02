package com.bt.BTbot.api.Utils;

import com.bt.BTbot.api.Utils.Interfaces.Mc;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class ItemUtil implements Mc {
    public static void useItem(EnumHand hand) {
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(hand));
        mc.player.swingArm(hand);
    }
}

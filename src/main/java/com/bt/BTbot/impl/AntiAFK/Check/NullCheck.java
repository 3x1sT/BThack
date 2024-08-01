package com.bt.BTbot.impl.AntiAFK.Check;

import com.bt.BTbot.api.Utils.Interfaces.Mc;

public class NullCheck implements Mc {
    public static boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }
}

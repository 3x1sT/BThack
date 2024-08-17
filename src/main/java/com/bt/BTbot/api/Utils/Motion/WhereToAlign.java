package com.bt.BTbot.api.Utils.Motion;

import com.bt.BThack.api.Utils.Interfaces.Mc;

public class WhereToAlign implements Mc {
    private double needX = 0;
    private double needZ = 0;

    public WhereToAlign() {}

    public double getNeedX() {
        return this.needX;
    }

    public double getNeedZ() {
        return this.needZ;
    }


    private void NumberMath() {
        needX = Math.floor(mc.player.posX) + 0.5;
        needZ = Math.floor(mc.player.posZ) + 0.5;
    }

    public void execute() {
        NumberMath();
    }
}
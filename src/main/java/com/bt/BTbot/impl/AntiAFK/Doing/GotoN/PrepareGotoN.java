package com.bt.BTbot.impl.AntiAFK.Doing.GotoN;

import com.bt.BTbot.api.Utils.Generate.GenerateIntNumber;
import com.bt.BTbot.impl.AntiAFK.Start.StartAntiAFK;
import com.bt.BTbot.impl.AntiAFK.Start.StartAntiAFKThread;

public class PrepareGotoN {
    public double newX;
    public double newZ;

    public PrepareGotoN() {
    }

    public void prepare() {
        if (StartAntiAFKThread.startDoing) return;
        StartAntiAFKThread.startDoing = true;
        double r = StartAntiAFK.walkRadius;
        int radius = (int) r;
        int a = GenerateIntNumber.generate(1, radius);
        int b = GenerateIntNumber.generate(1, radius);
        newX = a;
        newZ = b;
        boolean negative = GenerateIntNumber.generate(0, 1) != 0;

        if (!negative) {
            newX = StartAntiAFK.radiusCenterX + newX;
            newZ = StartAntiAFK.radiusCenterZ + newZ;
        } else {
            newX = StartAntiAFK.radiusCenterX - newX;
            newZ = StartAntiAFK.radiusCenterZ - newZ;
        }
    }
}

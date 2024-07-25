package com.bt.BTbot.api.Utils.Motion;

import com.bt.BThack.api.Utils.Interfaces.Mc;

public class AlignWithXZ implements Mc {

    private final WhereToAlign whereToAlign;
    AlignThread thread;


    public AlignWithXZ(WhereToAlign whereToAlign) {
        this.whereToAlign = whereToAlign;
    }

    public void alignWithXZ() {
        whereToAlign.execute();

        double minX = whereToAlign.getNeedX() - 0.15;
        double maxX = whereToAlign.getNeedX() + 0.15;

        double minZ = whereToAlign.getNeedZ() - 0.15;
        double maxZ = whereToAlign.getNeedZ() + 0.15;

        thread = new AlignThread(whereToAlign.getNeedX(), whereToAlign.getNeedZ(), minX, maxX, minZ, maxZ);
        thread.start();
    }

    public boolean isMoving() {
        return thread.isMoving;
    }
}

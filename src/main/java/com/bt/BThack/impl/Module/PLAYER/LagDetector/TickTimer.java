package com.bt.BThack.impl.Module.PLAYER.LagDetector;

public class TickTimer {
    protected long time = currentTime();

    protected long currentTime() {
        return System.currentTimeMillis();
    }

    public void reset(int offset) {
        reset((long) offset);
    }

    public void reset(long offset) {
        time = currentTime() + offset;
    }

    private final TimeUnit timeUnit;

    public TickTimer(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public boolean tick(long delay, boolean resetIfTick) {
        if (currentTime() - time > delay * timeUnit.multiplier) {
            if (resetIfTick) time = currentTime();
            return true;
        } else {
            return false;
        }
    }

    public enum TimeUnit {
        MILLISECONDS(1L),
        TICKS(50L),
        SECONDS(1000L),
        MINUTES(60000L);

        public final long multiplier;

        TimeUnit(long multiplier) {
            this.multiplier = multiplier;
        }
    }
}

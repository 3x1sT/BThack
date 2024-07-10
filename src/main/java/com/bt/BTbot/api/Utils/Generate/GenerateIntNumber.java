package com.bt.BTbot.api.Utils.Generate;

import java.util.Random;

public class GenerateIntNumber {
    private static final Random random = new Random();

    public static int generate(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}

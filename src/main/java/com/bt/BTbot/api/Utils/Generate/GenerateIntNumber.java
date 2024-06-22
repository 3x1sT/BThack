package com.bt.BTbot.api.Utils.Generate;

import java.util.Random;

public class GenerateIntNumber {
    public static int generate(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}

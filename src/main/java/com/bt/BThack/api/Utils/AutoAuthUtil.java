package com.bt.BThack.api.Utils;

import java.io.FileWriter;
import java.io.IOException;

public class AutoAuthUtil {
    public static void ReloadPassword(String Password) {
        try (FileWriter writer = new FileWriter("C:\\BThack\\AutoAuth\\Password.txt", false)){
            writer.write(Password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.bt.BThack.impl.Module.PLAYER.Spammer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadTXT {
    public static int Value = 1;
    public static void Read() {
        try {
            Path path = Paths.get("BThack/Spammer/Spammer.txt");
            if (Files.exists(path)) {
                BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                String line = reader.readLine();
                while (line != null) {
                    line = reader.readLine();
                    if (line != null)
                        Value = Value + 1;
                }
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

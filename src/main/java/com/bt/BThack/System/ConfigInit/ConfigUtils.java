package com.bt.BThack.System.ConfigInit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUtils {

    public static OutputStreamWriter createWriter(Path path) {
        try {
            return new OutputStreamWriter(Files.newOutputStream(path), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public static String jsonToString(JsonObject object) {
        return ConfigInit.gson.toJson(new JsonParser().parse(object.toString()));
    }
}

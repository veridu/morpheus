package com.veridu.morpheus.test.unit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by cassio on 11/11/16.
 */
public class MainTest {

    public static JsonObject loadJsonResponse(String resourcePath) {
        String filePath = TestIdOSSQL.class.getResource(resourcePath).getPath();
        String fileString = null;

        try {
            fileString = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JsonParser().parse(fileString).getAsJsonObject();
    }

}
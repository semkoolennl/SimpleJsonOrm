package org.semdevko.simplejsonorm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.logging.Logger;

public class JsonDatabase {
    private final Logger LOG = Logger.getLogger(JsonDatabase.class.getName());
    private final String filepath;
    private final Gson gson;
    private HashMap<String, String> data = new HashMap<>();

    public JsonDatabase(String filepath) {
        this.filepath = filepath;
        this.gson     = new Gson();
        initialize();
    }

    public String loadJson(String key) {
        return data.get(key);
    }

    public void saveJson(String key, String json) {
        data.put(key, json);
        try (Writer writer = new FileWriter(filepath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            LOG.severe("jsonDB save error: " + e.getMessage());
        }
    }

    public Gson getGson() {
        return gson;
    }

    private void initialize() {
        createFileIfItDoesNotExist();
        try (Reader reader = new FileReader(filepath)) {
            Type typeOfT = TypeToken.getParameterized(HashMap.class, String.class, String.class).getType();
            data = gson.fromJson(reader, typeOfT);
        } catch (IOException e) {
            LOG.severe("jsonDB load error: " + e.getMessage());
        }
    }

    private void createFileIfItDoesNotExist() {
        File file = new File(filepath);
        if (file.exists()) {
            return;
        }

        boolean success;
        try {
            if (!file.getParentFile().exists()) {
                success = file.getParentFile().mkdirs();
                if (!success) {
                    LOG.severe("jsonDB createFile error: could not create parent directory");
                }
            }
            success = file.createNewFile();
            if (!success) {
                LOG.severe("jsonDB createFile error: could not create file");
            }
        } catch (IOException e) {
            LOG.severe("jsonDB createFile error: " + e.getMessage());
        }
    }
}

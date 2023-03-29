package org.semdevko.simplejsonorm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.semdevko.simplejsonorm.core.JsonDatabase;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.logging.Logger;

public class DefaultJsonDatabase implements JsonDatabase {
    private final Logger LOG = Logger.getLogger(DefaultJsonDatabase.class.getName());
    private final String filepath;
    private final Gson gson;
    private HashMap<String, String> data = new HashMap<>();

    public DefaultJsonDatabase(String filepath) throws IOException {
        this.filepath = filepath;
        this.gson     = new Gson();
        initialize();
    }

    public String loadJson(String key) {
        return data.get(key);
    }

    public void saveJson(String key, String json) throws IOException {
        data.put(key, json);
        try (Writer writer = new FileWriter(filepath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new IOException("jsonDB save error: " + e.getMessage());
        }
    }

    public Gson getGson() {
        return gson;
    }

    private void initialize() throws IOException {
        createFileIfItDoesNotExist();
        try (Reader reader = new FileReader(filepath)) {
            Type typeOfT = TypeToken.getParameterized(HashMap.class, String.class, String.class).getType();
            data = gson.fromJson(reader, typeOfT);
        } catch (IOException e) {
            LOG.severe("jsonDB load error: " + e.getMessage());
        }
    }

    private void createFileIfItDoesNotExist() throws IOException {
        File file = new File(filepath);
        if (file.exists()) {
            return;
        }

        boolean success;
        try {
            if (!file.getParentFile().exists()) {
                success = file.getParentFile().mkdirs();
                if (!success) {
                    throw new IOException("jsonDB createFile error: could not create parent directory");
                }
            }
            success = file.createNewFile();
            if (!success) {
                throw new IOException("jsonDB createFile error: could not create file");
            }
        } catch (IOException e) {
            throw new IOException("jsonDB createFile error: " + e.getMessage());
        }
    }
}

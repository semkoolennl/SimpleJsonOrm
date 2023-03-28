package org.semdevko.simplejsonorm.core;

public interface JsonDatabase {
    void saveJson(String key, String json);
    String loadJson(String key);
}

package org.semdevko.simplejsonorm.core;

import java.io.IOException;

public interface JsonDatabase {
    void saveJson(String key, String json) throws IOException;
    String loadJson(String key);
}

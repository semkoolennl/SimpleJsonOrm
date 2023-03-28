package org.semdevko.simplejsonorm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.semdevko.simplejsonorm.core.Entity;
import org.semdevko.simplejsonorm.core.Repository;
import org.semdevko.simplejsonorm.core.JsonDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

abstract class AbstractJsonRepository<E extends Entity> implements Repository<E> {
    private final Logger LOG = Logger.getLogger(AbstractJsonRepository.class.getName());
    private final JsonDatabase jsonDB;
    private final Gson gson;
    private final Class<E> clazz;
    protected HashMap<Integer, E> entities = new HashMap<>();

    public AbstractJsonRepository(JsonDatabase jsonDB, Class<E> clazz) {
        this.gson   = new Gson();
        this.jsonDB = jsonDB;
        this.clazz  = clazz;

        loadEntitiesFromJson();
    }

    public void save(E entity) {
        if (entity.getId() == -1) {
            entity.setId(determineNextId());
        }
        entities.put(entity.getId(), entity);
        persist();
    }

    public E findById(int id) {
        return entities.get(id);
    }

    public ArrayList<E> findAll() {
        return new ArrayList<>(entities.values());
    }

    public boolean delete(E entity) {
        return delete(entity.getId());
    }

    public boolean delete(int id) {
        if (!entities.containsKey(id)) {
            return false;
        }
        entities.remove(id);
        persist();
        return true;
    }

    public void deleteAll() {
        entities.clear();
        persist();
    }

    public ArrayList<E> searchByField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        ArrayList<E> result = new ArrayList<>();
        for (E entity : entities.values()) {
            Object fieldValue = getEntityFieldValue(fieldName, entity);
            if (fieldValue != null && fieldValue.equals(value)) {
                result.add(entity);
            }
        }
        return result;
    }

    private Object getEntityFieldValue(String fieldName, E entity) throws NoSuchFieldException, IllegalAccessException {
        Field field = entity.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(entity);
    }

    private int determineNextId() {
        int maxId = 0;
        for (E entity : entities.values()) {
            if (entity.getId() > maxId) {
                maxId = entity.getId();
            }
        }
        return maxId + 1;
    }

    private void persist() {
        jsonDB.saveJson(clazz.getName(), gson.toJson(entities.values()));
    }

    private void loadEntitiesFromJson() {
        String json = jsonDB.loadJson(clazz.getName());

        Type typeOfT = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        ArrayList<E> array = gson.fromJson(json, typeOfT);
        if (array == null) {
            return;
        }
        for (E entity : array) {
            entities.put(entity.getId(), entity);
        }
    }
}
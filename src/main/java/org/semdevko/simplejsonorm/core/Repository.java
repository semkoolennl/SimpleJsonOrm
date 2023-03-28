package org.semdevko.simplejsonorm.core;

import java.util.ArrayList;

public interface Repository<E extends Entity> {
    void save(E entity);
    E findById(int id);
    ArrayList<E> findAll();
    ArrayList<E> searchByField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException;
    boolean delete(E entity);
    boolean delete(int id);
    void deleteAll();
}

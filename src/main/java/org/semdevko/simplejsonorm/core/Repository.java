package org.semdevko.simplejsonorm.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public interface Repository<E extends Entity> {
    void save(E entity) throws IOException;
    Optional<E> findById(int id);
    ArrayList<E> findAll();
    ArrayList<E> searchByField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException;
    boolean delete(E entity) throws IOException;
    boolean delete(int id) throws IOException;
    void deleteAll() throws IOException;
}

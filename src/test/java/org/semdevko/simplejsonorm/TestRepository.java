package org.semdevko.simplejsonorm;

import org.semdevko.simplejsonorm.core.JsonDatabase;

public class TestRepository extends AbstractJsonRepository<TestEntity> {
    public TestRepository(JsonDatabase jsonDB, Class<TestEntity> clazz) {
        super(jsonDB, clazz);
    }
}

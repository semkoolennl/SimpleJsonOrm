package org.semdevko.simplejsonorm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semdevko.simplejsonorm.core.JsonDatabase;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class AbstractJsonRepositoryTest {
    private AbstractJsonRepository<TestEntity> repository;

    @Before
    public void setUp() throws Exception {
        JsonDatabase database = new DefaultJsonDatabase("db.json");
        repository = new TestRepository(database, TestEntity.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void save() throws IOException {
        repository.save(new TestEntity("name1", "description1"));
    }

    @Test
    public void findById() {
        Optional<TestEntity> optional = repository.findById(1);
        assertTrue(optional.isPresent());

        TestEntity entity = optional.get();
    }

    @Test
    public void findAll() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void searchByField() {
    }
}
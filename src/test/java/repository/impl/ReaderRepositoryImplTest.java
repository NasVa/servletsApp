package repository.impl;

import models.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import repository.ReaderRepository;

class ReaderRepositoryImplTest {

    private static final PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("postgres").withUsername("username").withPassword("password")
            .withInitScript("test-schema.sql");
    ReaderRepository readerRepository = new ReaderRepositoryImpl(() -> postgreSQLContainer.createConnection(""));

    @BeforeAll
    public static void setup() {
        postgreSQLContainer.start();
    }

    @Test
    void saveAndDelete() {
        Reader reader = getReader();
        readerRepository.save(reader);
        Reader readerFromDb = readerRepository.getById(reader.getId());
        Assertions.assertEquals(reader, readerFromDb);
        int deleted = readerRepository.deleteById(reader.getId());
        Assertions.assertEquals(1, deleted);
        Reader noReader = readerRepository.getById(reader.getId());
        Assertions.assertNull(noReader);
    }

    private static Reader getReader() {
        Reader reader = new Reader();
        reader.setName("nastya");
        reader.setMail("mail@mail");
        reader.setPhone(101);
        return reader;
    }

    @Test
    void update() {
        Reader reader = getReader();
        readerRepository.save(reader);
        reader.setName("lala");
        reader.setPhone(0);
        reader.setMail("@");
        readerRepository.save(reader);
        Reader readerFromDb = readerRepository.getById(reader.getId());
        Assertions.assertEquals(reader, readerFromDb);
    }
}
package repository;

import models.Reader;

public interface ReaderRepository {
    Reader getById(int id);

    void save(Reader reader);

    int deleteById(Integer id);
}

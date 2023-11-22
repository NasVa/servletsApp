package repository;

import models.Reader;

public interface ReaderRepository {
    Reader getById(Integer id);

    void save(Reader reader);

    int deleteById(Integer id);
}

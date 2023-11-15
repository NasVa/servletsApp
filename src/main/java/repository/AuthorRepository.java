package repository;

import models.Author;

public interface AuthorRepository {
    Author getById(int id);

    void save(Author author);

    int deleteById(Integer id);
}

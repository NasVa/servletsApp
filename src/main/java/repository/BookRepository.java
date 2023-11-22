package repository;

import models.Book;

public interface BookRepository {
    Book getById(Integer id);

    void save(Book book);

    int deleteById(Integer id);
}

package repository;

import models.Book;

public interface BookRepository {
    Book getById(int id);

    void save(Book book);

    int deleteById(Integer id);
}

package repository;

import models.Author;
import models.Book;

import java.util.List;

public interface AuthorRepository {
    List<Book> getAuthorBooks(Integer authorId);

    List<Author> getById(List<Integer> ids);

    Author getById(Integer id);

    void save(Author author);

    int deleteById(Integer id);
}

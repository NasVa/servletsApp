package repository.impl;

import models.Author;
import models.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.ReaderRepository;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryImplTest {

    private static final PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("postgres").withUsername("username").withPassword("password")
            .withInitScript("test-schema.sql");
    BookRepository bookRepository = new BookRepositoryImpl(() -> postgreSQLContainer.createConnection(""));
    AuthorRepository authorRepository = new AuthorRepositoryImpl(() -> postgreSQLContainer.createConnection(""));

    @BeforeAll
    public static void setup() {
        postgreSQLContainer.start();
    }

    @Test
    void getById() {
        Book book = new Book();
        book.setName("book");
        book.setPages(10);
        book.setLocationOfPublishing("Minsk");
        book.setYearOfPublishing(2020);
        bookRepository.save(book);

        Book bookFromDb = bookRepository.getById(book.getId());
        Assertions.assertEquals(bookFromDb, book);
    }

    @Test
    void save() {
        Book book = new Book();
        book.setName("book");
        book.setPages(10);
        book.setLocationOfPublishing("Minsk");
        book.setYearOfPublishing(2020);
        bookRepository.save(book);
        Book bookFromDb = bookRepository.getById(book.getId());
        Assertions.assertEquals(book, bookFromDb);
    }

    @Test
    void deleteById() throws SQLException {
        Book book = new Book();
        book.setName("book");
        book.setPages(10);
        book.setLocationOfPublishing("Minsk");
        book.setYearOfPublishing(2020);
        ArrayList<Author> authors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Author author = new Author();
            author.setName("Author" + i);
            authors.add(author);
            authorRepository.save(author);
        }
        book.setAuthors(authors);
        bookRepository.save(book);
        bookRepository.deleteById(book.getId());
        Connection connection = postgreSQLContainer.createConnection("");
        PreparedStatement ps = connection.prepareStatement("SELECT FROM authors_books author_id WHERE book_id = ?");
        ps.setInt(1, book.getId());
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        Assertions.assertFalse(resultSet.next());
    }
}
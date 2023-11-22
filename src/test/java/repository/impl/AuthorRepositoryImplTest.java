package repository.impl;

import models.Author;
import models.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.ReaderRepository;

import java.util.ArrayList;

class AuthorRepositoryImplTest {

    private static final PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("postgres").withUsername("username").withPassword("password")
            .withInitScript("schema.sql");
    AuthorRepository authorRepository = new AuthorRepositoryImpl(() -> postgreSQLContainer.createConnection(""));
    ReaderRepository readerRepository = new ReaderRepositoryImpl(() -> postgreSQLContainer.createConnection(""));
    BookRepository bookRepository = new BookRepositoryImpl(() -> postgreSQLContainer.createConnection(""), readerRepository);

    @BeforeAll
    public static void setup() {
        postgreSQLContainer.start();
    }
    @Test
    void getById() {
        Author author = new Author();
        author.setName("authorname");
        authorRepository.save(author);
        Author authorFromDb = authorRepository.getById(author.getId());
        Assertions.assertEquals(author, authorFromDb);
    }

    @Test
    void save() {
        Author author = new Author();
        author.setName("author");
        authorRepository.save(author);
        author.setName("new");
        authorRepository.save(author);
        Author authorFromDb = authorRepository.getById(author.getId());
        Assertions.assertEquals(author, authorFromDb);
    }

    @Test
    void deleteById() {
        Author author = new Author();
        author.setName("author");
        authorRepository.save(author);
        authorRepository.deleteById(author.getId());
        Author authorFromDb = authorRepository.getById(author.getId());
        Assertions.assertNull(authorFromDb);
    }

    @Test
    void authorWithBooksTest(){
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Book book = new Book();
            book.setName("book" + i);
            book.setPages(100);
            bookRepository.save(book);
        }
        Author author = new Author();
        author.setBooks(books);
        author.setName("nnn");
        authorRepository.save(author);
        Author authorFromDb = authorRepository.getById(author.getId());
        Assertions.assertEquals(author, authorFromDb);
    }
}
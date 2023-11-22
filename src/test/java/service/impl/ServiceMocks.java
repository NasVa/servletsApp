package service.impl;

import mapper.impl.AuthorMapper;
import mapper.impl.BookMapper;
import mapper.impl.ReaderMapper;
import models.Author;
import models.Book;
import models.Reader;
import org.mockito.Mockito;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.ReaderRepository;
import service.AuthorService;
import service.BookService;
import service.ReaderService;

import java.util.List;

import static org.mockito.Mockito.doAnswer;

public class ServiceMocks {
    static BookRepository bookRepository;
    static ReaderRepository readerRepository;
    static AuthorRepository authorRepository;
    static BookService bookService;
    static ReaderService readerService;
    static AuthorService authorService;
    static BookMapper bookMapper;
    static ReaderMapper readerMapper;
    static AuthorMapper authorMapper;
    static final int BOOK_SAVE_ID = 12;
    static final int READER_SAVE_ID = 13;
    static final int AUTHOR_SAVE_ID = 14;


    static {
        bookRepository = Mockito.mock(BookRepository.class);
        readerRepository = Mockito.mock(ReaderRepository.class);
        authorRepository = Mockito.mock(AuthorRepository.class);

        doAnswer(invocationOnMock -> {
            Book argument = invocationOnMock.getArgument(0, Book.class);
            if (argument.getId() == null) {
                argument.setId(BOOK_SAVE_ID);
            }
            return null;
        }).when(bookRepository).save(Mockito.any());
        doAnswer(invocationOnMock -> 1).when(bookRepository).deleteById(Mockito.any());
        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0, Integer.class);
            Book book = createTestBook(id);
            book.setOwner(createTestReader(24));
            book.setAuthors(List.of(createTestAuthor(1), createTestAuthor(2)));
            return book;
        }).when(bookRepository).getById(Mockito.any());

        doAnswer(invocationOnMock -> {
            Reader argument = invocationOnMock.getArgument(0, Reader.class);
            if (argument.getId() == null) {
                argument.setId(READER_SAVE_ID);
            }
            return null;
        }).when(readerRepository).save(Mockito.any());
        doAnswer(invocationOnMock -> 1).when(readerRepository).deleteById(Mockito.any());
        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0, Integer.class);
            return createTestReader(id);
        }).when(readerRepository).getById(Mockito.any());

        doAnswer(invocationOnMock -> {
            Author argument = invocationOnMock.getArgument(0, Author.class);
            if (argument.getId() == null) {
                argument.setId(AUTHOR_SAVE_ID);
            }
            return null;
        }).when(authorRepository).save(Mockito.any());
        doAnswer(invocationOnMock -> 1).when(authorRepository).deleteById(Mockito.any());
        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0, Integer.class);
            Author author = createTestAuthor(id);
            author.setBooks(List.of(createTestBook(666)));
            return author;
        }).when(authorRepository).getById(Mockito.any(Integer.class));
        doAnswer(invocationOnMock -> {
            List<Integer> ids = invocationOnMock.getArgument(0, List.class);
            AuthorRepository self = (AuthorRepository) invocationOnMock.getMock();
            return ids.stream().map(self::getById).toList();
        }).when(authorRepository).getById(Mockito.any(List.class));
        doAnswer(invocationOnMock -> {
            Integer id = invocationOnMock.getArgument(0, Integer.class);
            return List.of(createTestBook(66), createTestBook(77));
        }).when(authorRepository).getAuthorBooks(Mockito.any());


        bookMapper = new BookMapper(readerRepository, authorRepository);
        readerMapper = new ReaderMapper();
        authorMapper = new AuthorMapper(authorRepository);

        bookService = new BookServiceImpl(bookRepository, bookMapper);
        readerService = new ReaderServiceImpl(readerRepository);
        authorService = new AuthorServiceImpl(authorRepository, authorMapper);
    }

    static Book createTestBook(int id) {
        Book book = new Book();
        book.setId(id);
        book.setName("Name");
        book.setPages(111);
        book.setYearOfPublishing(2001);
        book.setLocationOfPublishing("M");
        return book;
    }

    static Reader createTestReader(int id) {
        Reader reader = new Reader();
        reader.setId(id);
        reader.setMail("mail@mail.su");
        reader.setName("Bob");
        reader.setPhone(2224);
        return reader;
    }

    static Author createTestAuthor(int id) {
        Author author = new Author();
        author.setId(id);
        return author;
    }
}

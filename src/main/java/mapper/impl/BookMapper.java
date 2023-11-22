package mapper.impl;

import dto.BookDto;
import mapper.Mapper;
import models.Author;
import models.Book;
import repository.AuthorRepository;
import repository.ReaderRepository;
import repository.impl.AuthorRepositoryImpl;
import repository.impl.ReaderRepositoryImpl;

public class BookMapper implements Mapper<Book, BookDto> {
    private static class InstanceHolder {
        private static final BookMapper INSTANCE = new BookMapper();
    }

    public static BookMapper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final ReaderRepository readerRepository;
    private final AuthorRepository authorRepository;

    public BookMapper(ReaderRepository readerRepository, AuthorRepository authorRepository) {
        this.readerRepository = readerRepository;
        this.authorRepository = authorRepository;
    }

    public BookMapper(){
        readerRepository = ReaderRepositoryImpl.getInstance();
        authorRepository = AuthorRepositoryImpl.getInstance();
    }

    @Override
    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setPages(book.getPages());
        bookDto.setLocationOfPublishing(book.getLocationOfPublishing());
        bookDto.setYearOfPublishing(book.getYearOfPublishing());
        bookDto.setOwnerId(book.getOwner().getId());
        bookDto.setAuthorsId(book.getAuthors().stream().map(Author::getId).toList());
        return bookDto;
    }

    @Override
    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setPages(bookDto.getPages());
        book.setLocationOfPublishing(bookDto.getLocationOfPublishing());
        book.setYearOfPublishing(bookDto.getYearOfPublishing());
        book.setOwner(readerRepository.getById(bookDto.getOwnerId()));
        book.setAuthors(authorRepository.getById(bookDto.getAuthorsId()));
        return book;
    }
}

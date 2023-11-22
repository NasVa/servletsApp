package service.impl;

import dto.BookDto;
import exception.NotFondException;
import exception.ValidationException;
import mapper.impl.BookMapper;
import models.Book;
import repository.BookRepository;
import repository.impl.BookRepositoryImpl;
import service.BookService;

public class BookServiceImpl implements BookService {

    private static class InstanceHolder {
        private static final BookService INSTANCE = new BookServiceImpl(BookRepositoryImpl.getInstance(), BookMapper.getInstance());
    }

    public static BookService getInstance() {
        return BookServiceImpl.InstanceHolder.INSTANCE;
    }

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto create(BookDto bookDto) throws ValidationException {
        if (bookDto.getId() != null){
            throw new ValidationException("Book id must be null");
        }
        Book entity = bookMapper.toEntity(bookDto);
        bookRepository.save(entity);
        return bookMapper.toDto(entity);
    }

    @Override
    public int delete(int id){
        return bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(BookDto bookDto) throws ValidationException {
        if (bookDto.getId() == null){
            throw new ValidationException("Book id must be not null");
        }
        Book entity = bookMapper.toEntity(bookDto);
        bookRepository.save(entity);
        return bookMapper.toDto(entity);
    }

    @Override
    public BookDto getById(int id) throws NotFondException {
        Book book = bookRepository.getById(id);
        if (book == null){
            throw new NotFondException("No one book found");
        }
        return bookMapper.toDto(book);
    }
}

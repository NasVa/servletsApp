package service;

import dto.BookDto;
import exception.NotFondException;
import exception.ValidationException;

public interface BookService {
    BookDto create(BookDto bookDto) throws ValidationException;

    int delete(int id);

    BookDto update(BookDto bookDto) throws ValidationException;

    BookDto getById(int id) throws NotFondException;
}

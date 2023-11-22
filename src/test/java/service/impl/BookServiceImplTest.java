package service.impl;

import dto.BookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static service.impl.ServiceMocks.*;

@SuppressWarnings("unchecked")
class BookServiceImplTest {
    @Test
    void createTest() {
        BookDto bookDto = createBookDto();
        BookDto actual = bookService.create(bookDto);
        bookDto.setId(ServiceMocks.BOOK_SAVE_ID);
        Assertions.assertEquals(bookDto, actual);
    }

    @Test
    void getByIdTest() {
        BookDto expected = bookMapper.toDto(bookRepository.getById(129));
        BookDto actual = bookService.getById(129);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        BookDto expected = createBookDto();
        expected.setId(12);
        BookDto actual = bookService.update(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        Assertions.assertEquals(1, bookService.delete(11));
    }

    private static BookDto createBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setName("book");
        bookDto.setPages(10);
        bookDto.setYearOfPublishing(2000);
        bookDto.setLocationOfPublishing("M");
        bookDto.setAuthorsId(List.of(1));
        bookDto.setOwnerId(2);
        return bookDto;
    }
}

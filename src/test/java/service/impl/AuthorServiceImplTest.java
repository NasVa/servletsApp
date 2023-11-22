package service.impl;

import dto.AuthorDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static service.impl.ServiceMocks.*;

public class AuthorServiceImplTest {
    @Test
    void createTest() {
        AuthorDto authorDto = createAuthorDto();
        AuthorDto actual = authorService.create(authorDto);
        authorDto.setId(AUTHOR_SAVE_ID);
        Assertions.assertEquals(authorDto, actual);
    }

    @Test
    void getByIdTest() {
        AuthorDto expected = authorMapper.toDto(authorRepository.getById(129));
        AuthorDto actual = authorService.getById(129);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        AuthorDto expected = createAuthorDto();
        expected.setId(14);
        AuthorDto actual = authorService.update(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        Assertions.assertEquals(1, authorService.delete(11));
    }

    private static AuthorDto createAuthorDto() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("author");
        authorDto.setBooksId(List.of(66, 77));
        return authorDto;
    }
}

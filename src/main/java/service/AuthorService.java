package service;

import dto.AuthorDto;

public interface AuthorService {
    AuthorDto create(AuthorDto authorDto);

    int delete(int id);

    AuthorDto update(AuthorDto authorDto);

    AuthorDto getById(int id);
}

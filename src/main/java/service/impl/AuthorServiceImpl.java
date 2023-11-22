package service.impl;

import dto.AuthorDto;
import exception.NotFondException;
import exception.ValidationException;
import mapper.impl.AuthorMapper;
import models.Author;
import repository.AuthorRepository;
import repository.impl.AuthorRepositoryImpl;

public class AuthorServiceImpl implements service.AuthorService {
    private static class InstanceHolder{
        private static final AuthorServiceImpl INSTANCE = new AuthorServiceImpl(AuthorRepositoryImpl.getInstance(), AuthorMapper.getInstance());
    }
    public static AuthorServiceImpl getInstance() { return InstanceHolder.INSTANCE;}
    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        if (authorDto.getId() != null) {
            throw new ValidationException("Reader id must be null");
        }
        Author entity = authorMapper.toEntity(authorDto);
        authorRepository.save(entity);
        return authorMapper.toDto(entity);
    }

    @Override
    public int delete(int id) {
        return authorRepository.deleteById(id);
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        if (authorDto.getId() == null) {
            throw new ValidationException("Reader id must be not null");
        }
        Author entity = authorMapper.toEntity(authorDto);
        authorRepository.save(entity);
        return authorMapper.toDto(entity);
    }

    @Override
    public AuthorDto getById(int id) {
        Author author = authorRepository.getById(id);
        if (author == null) {
            throw new NotFondException("No one reader found");
        }
        return authorMapper.toDto(author);
    }
}

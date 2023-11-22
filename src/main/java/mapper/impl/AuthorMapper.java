package mapper.impl;

import dto.AuthorDto;
import mapper.Mapper;
import models.Author;
import models.Book;
import repository.AuthorRepository;
import repository.impl.AuthorRepositoryImpl;

public class AuthorMapper implements Mapper<Author, AuthorDto> {
    private static class InstanceHolder{
        private static final AuthorMapper INSTANCE = new AuthorMapper(AuthorRepositoryImpl.getInstance());
    }
    public static AuthorMapper getInstance() { return AuthorMapper.InstanceHolder.INSTANCE;}
    private final AuthorRepository authorRepository;

    public AuthorMapper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorDto toDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBooksId(author.getBooks().stream().map(Book::getId).toList());
        return authorDto;
    }

    @Override
    public Author toEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setBooks(authorRepository.getAuthorBooks(author.getId()));
        return author;
    }
}

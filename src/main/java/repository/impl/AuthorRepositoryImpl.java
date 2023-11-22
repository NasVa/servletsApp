package repository.impl;

import db.ConnectionManager;
import db.impl.ConnectionManagerImpl;
import models.Author;
import models.Book;
import repository.AuthorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {
    private static final String CREATE_AUTHOR = "INSERT INTO authors (name) VALUES (?)";
    private static final String UPDATE_AUTHOR = "UPDATE authors SET name = ? WHERE id = ?";
    private static final String GET_AUTHOR = "SELECT id, name FROM authors";
    private static final String DELETE_AUTHOR = "DELETE FROM authors WHERE id = ?";
    private static final String GET_AUTHORS_BY_ID = GET_AUTHOR + " WHERE id IN (%s)";
    private static final String GET_AUTHOR_BOOKS = "SELECT book_id FROM authors_books WHERE author_id = ?";
    private static class InstanceHolder{
        private static final AuthorRepository INSTANCE = new AuthorRepositoryImpl(ConnectionManagerImpl.getInstance());
    }
    public static AuthorRepository getInstance(){
        return InstanceHolder.INSTANCE;
    }

    private final ConnectionManager connectionManager;

    public AuthorRepositoryImpl(ConnectionManager connectionManager) { this.connectionManager = connectionManager; }

    @Override
    public List<Book> getAuthorBooks(Integer authorId){
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_AUTHOR_BOOKS)) {
            ps.setInt(1, authorId);
            ps.executeQuery();
            ResultSet resultSet = ps.getResultSet();
            ArrayList<Book> books = new ArrayList<>();
            while(resultSet.next()){
                books.add(BookRepositoryImpl.getInstance().getById(resultSet.getInt(1)));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> getById(List<Integer> ids){
        String req = GET_AUTHORS_BY_ID.formatted(String.join(",", Collections.nCopies(ids.size(), "?")));

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(req)) {
            for (int i = 1; i <= ids.size(); i++) {
                ps.setInt(i, ids.get(i - 1));
            }
            ps.executeQuery();
            ResultSet resultSet = ps.getResultSet();
            ArrayList<Author> authors = new ArrayList<>();
            while(resultSet.next()){
                Author author = new Author();
                author.setId(resultSet.getInt(1));
                author.setName(resultSet.getString(2));
                author.setBooks(getAuthorBooks(author.getId()));
                authors.add(author);
            }
            return authors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Author getById(Integer id) {
        List<Author> authors = getById(List.of(id));
        if(authors.isEmpty()){
            return null;
        }
        return authors.get(0);
    }

    private Author toEntity(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getInt("id"));
        author.setName(resultSet.getString("name"));
        return author;
    }

    @Override
    public void save(Author author) {
        if (author.getId() == null) {
            create(author);
        } else {
            update(author);
        }
    }

    private void update(Author author) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_AUTHOR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author.getName());
            ps.setInt(2, author.getId());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                author.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void create(Author author) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_AUTHOR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author.getName());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                author.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_AUTHOR)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

package repository.impl;

import db.ConnectionManager;
import db.impl.ConnectionManagerImpl;
import models.Author;
import models.Book;
import repository.BookRepository;
import repository.ReaderRepository;

import java.sql.*;
import java.util.ArrayList;

public class BookRepositoryImpl implements BookRepository {
    private static class InstanceHolder {
        private static final BookRepository INSTANCE = new BookRepositoryImpl(ConnectionManagerImpl.getInstance(),
                ReaderRepositoryImpl.getInstance());
    }

    private static final String CREATE_BOOK = "INSERT INTO books (name, yearOfPublishing, locationOfPublishing, pages, owner_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE books SET name = ?, yearOfPublishing = ?, locationOfPublishing = ?, pages = ?, owner_id = ?  WHERE id = ?";
    private static final String GET_BOOK = "SELECT id, name, yearOfPublishing, locationOfPublishing, pages, owner_id FROM books";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id = ?";
    private static final String DELETE_BOOK_AUTHORS = "DELETE FROM authors_books WHERE book_id = ?";
    private static final String GET_BOOK_BY_ID = GET_BOOK + " WHERE id = ?";
    private static final String SET_BOOK_AUTHORS = "INSERT INTO authors_books (author_id, book_id) VALUES (?, ?)";
    private static final String GET_BOOK_AUTHORS = "SELECT author_id FROM authors_books WHERE book_id = ?";
    public static BookRepository getInstance() {
        return BookRepositoryImpl.InstanceHolder.INSTANCE;
    }

    private final ConnectionManager connectionManager;
    private final ReaderRepository readerRepository;

    public BookRepositoryImpl(ConnectionManager connectionManager, ReaderRepository readerRepository) {
        this.connectionManager = connectionManager;
        this.readerRepository = readerRepository;
    }

    private ArrayList<Author> getBookAuthors(int bookId){
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BOOK_AUTHORS)) {
            ps.setInt(1, bookId);
            ps.executeQuery();
            ResultSet resultSet = ps.getResultSet();
            ArrayList<Author> authors = new ArrayList<>();
            while(resultSet.next()){
                authors.add(AuthorRepositoryImpl.getInstance().getById(resultSet.getInt(1)));
            }
            return authors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Book getById(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BOOK_BY_ID)) {
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet resultSet = ps.getResultSet();
            if (resultSet.next()) {
                Book book = toEntity(resultSet);
                book.setOwner(readerRepository.getById(resultSet.getInt(6)));
                book.setAuthors(getBookAuthors(book.getId()));
                return book;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Book toEntity(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setName(resultSet.getString("name"));
        book.setYearOfPublishing(resultSet.getInt("yearOfPublishing"));
        book.setLocationOfPublishing(resultSet.getString("locationOfPublishing"));
        book.setPages(resultSet.getInt("pages"));
        return book;
    }

    @Override
    public void save(Book book) {
        if (book.getId() == null) {
            create(book);
        } else {
            update(book);
        }
    }

    private void update(Book book) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getName());
            ps.setInt(2, book.getYearOfPublishing());
            ps.setString(3, book.getLocationOfPublishing());
            ps.setInt(4, book.getPages());
            ps.setInt(5, book.getOwner().getId());
            ps.setInt(6, book.getId());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getInt(1));
            }
            setBookAuthors(book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteBookAuthors(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_BOOK_AUTHORS)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setBookAuthors(Book book) {
        deleteBookAuthors(book.getId());
        try (Connection connection = connectionManager.getConnection()) {
            if (book.getAuthors() != null) {
                for (int i = 0; i < book.getAuthors().size(); i++) {
                    try(PreparedStatement ps = connection.prepareStatement(SET_BOOK_AUTHORS)) {
                        ps.setInt(1, book.getAuthors().get(i).getId());
                        ps.setInt(2, book.getId());
                        ps.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void create(Book book) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getName());
            ps.setInt(2, book.getYearOfPublishing());
            ps.setString(3, book.getLocationOfPublishing());
            ps.setInt(4, book.getPages());
            if (book.getOwner() != null) {
                ps.setInt(5, book.getOwner().getId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getInt(1));
            }
            setBookAuthors(book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Integer id) {
        deleteBookAuthors(id);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_BOOK)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

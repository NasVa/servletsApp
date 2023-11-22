package repository.impl;

import db.ConnectionManager;
import db.impl.ConnectionManagerImpl;
import models.Reader;
import repository.ReaderRepository;

import java.sql.*;

public class ReaderRepositoryImpl implements ReaderRepository {
    private static class InstanceHolder {
        private static final ReaderRepository INSTANCE = new ReaderRepositoryImpl(ConnectionManagerImpl.getInstance());
    }
    private static final String CREATE_READER = "INSERT INTO readers (name, mail, phone) VALUES (?, ?, ?)";
    private static final String UPDATE_READER = "UPDATE readers SET name = ?, mail = ?, phone = ? WHERE id = ?";
    private static final String GET_READER = "SELECT id, name, mail, phone FROM readers";
    private static final String DELETE_READER = "DELETE FROM readers WHERE id = ?";
    private static final String GET_READER_BY_ID = GET_READER + " WHERE id = ?";

    public static ReaderRepository getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final ConnectionManager connectionManager;

    public ReaderRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Reader getById(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_READER_BY_ID)) {
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet resultSet = ps.getResultSet();
            if (resultSet.next()) {
                return toEntity(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Reader toEntity(ResultSet resultSet) throws SQLException {
        Reader reader = new Reader();
        reader.setId(resultSet.getInt("id"));
        reader.setName(resultSet.getString("name"));
        reader.setMail(resultSet.getString("mail"));
        reader.setPhone(resultSet.getInt("phone"));
        return reader;
    }

    @Override
    public void save(Reader reader) {
        if (reader.getId() == null) {
            create(reader);
        } else {
            update(reader);
        }
    }

    private void update(Reader reader) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_READER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, reader.getName());
            ps.setString(2, reader.getMail());
            ps.setInt(3, reader.getPhone());
            ps.setInt(4, reader.getId());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                reader.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void create(Reader reader) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_READER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, reader.getName());
            ps.setString(2, reader.getMail());
            ps.setInt(3, reader.getPhone());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                reader.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_READER)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

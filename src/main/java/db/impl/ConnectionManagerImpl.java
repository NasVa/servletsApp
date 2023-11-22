package db.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import db.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManagerImpl implements ConnectionManager {

    private ConnectionManagerImpl() {
        try {
            Properties prop = new Properties();
            prop.load(ConnectionManager.class.getClassLoader().getResourceAsStream("datasource.properties"));
            for (Object key : prop.keySet()) {
                if (key instanceof String stringKey) {
                    String envValue = System.getenv(stringKey.toUpperCase());
                    if (envValue != null) {
                        prop.setProperty(stringKey, envValue);
                    }
                }
            }
            HikariConfig config = new HikariConfig(prop);
            ds = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class InstanceHolder {
        private static final ConnectionManager INSTANCE = new ConnectionManagerImpl();
    }

    public static ConnectionManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final HikariDataSource ds;

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}

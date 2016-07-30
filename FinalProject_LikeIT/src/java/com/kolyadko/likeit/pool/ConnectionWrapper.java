package com.kolyadko.likeit.pool;


import com.kolyadko.likeit.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */
public class ConnectionWrapper {
    private static final Logger LOG = LogManager.getLogger(ConnectionWrapper.class);

    private Connection connection;

    ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void close() {
        try {
            ConnectionPool.getInstance().closeConnection(this);
        } catch (ConnectionPoolException e) {
            LOG.error(e.getMessage());
        }
    }

    void finallyClose() throws SQLException {
        connection.close();

        if (connection.isClosed()) {
            LOG.info("Connection wss really closed");
            LOG.info("isValid() = " + connection.isValid(0));
        } else {
            LOG.info("Connection is still alive! WTF?");
        }
    }

    public boolean isClosed() throws SQLException {
        return connection.isClosed();
    }

    public Savepoint setSavepoint() throws SQLException {
        return connection.setSavepoint();
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        return connection.setSavepoint(name);
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        connection.rollback();
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        connection.releaseSavepoint(savepoint);
    }

    public boolean isValid(int timeout) throws SQLException {
        return connection.isValid(timeout);
    }
}
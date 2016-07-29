package com.kolyadko.likeit.dao;


import com.kolyadko.likeit.entity.Entity;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */
public abstract class AbstractDao<K, T extends Entity> {
    protected ConnectionWrapper connection;
    protected static final Logger LOG = LogManager.getLogger(AbstractDao.class);

    public AbstractDao(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public abstract T findById(K id) throws DaoException;

    public abstract ArrayList<T> findAll() throws DaoException;

    public abstract void create(T user) throws DaoException;

    protected abstract T readEntity(ResultSet resultSet) throws SQLException;

    protected void closeStatement(Statement statement) {
        if (!checkNull(statement)) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
    }

    protected ArrayList<T> findWithStatement(String query) throws SQLException {
        ArrayList<T> users = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                users.add(readEntity(resultSet));
            }
        } finally {
            closeStatement(statement);
        }

        return users;
    }

    private boolean checkNull(Object object) {
        return object == null;
    }
}
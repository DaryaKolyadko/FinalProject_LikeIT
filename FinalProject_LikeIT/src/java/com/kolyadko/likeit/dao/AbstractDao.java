package com.kolyadko.likeit.dao;


import com.kolyadko.likeit.entity.Entity;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */
public abstract class AbstractDao<K, T extends Entity> {
    protected ConnectionProxy connection;
    protected static final Logger LOG = LogManager.getLogger(AbstractDao.class);

    public AbstractDao(ConnectionProxy connection) {
        this.connection = connection;
    }

    public abstract boolean create(T entity) throws DaoException;

    public abstract T readEntity(ResultSet resultSet) throws DaoException;

    protected ArrayList<T> findWithStatement(String query) throws DaoException {
        ArrayList<T> entities = new ArrayList<>();
        ResultSet resultSet;

        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                entities.add(readEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return entities;
    }

    protected ArrayList<T> findBy(String query, Object... params) throws DaoException {
        ArrayList<T> entities = new ArrayList<>();
        ResultSet resultSet;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParams(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entities.add(readEntity(resultSet));
            }

            return entities;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected T findOnlyOne(String query, Object param) throws DaoException {
        ArrayList<T> entities = findBy(query, param);

        if (!checkNull(entities) && !entities.isEmpty()) {
            return entities.get(0);
        }

        return null;
    }

    protected boolean updateEntityWithQuery(String query, Object... params) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParams(preparedStatement, params);
            preparedStatement.executeUpdate();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void setParams(PreparedStatement preparedStatement, Object... params) throws DaoException {
        try {
            int counter = 1;

            for (Object param : params) {
                if (!checkNull(param)) {
                    preparedStatement.setObject(counter, param);
                    counter++;
                } else {
                    LOG.warn("Null param was passed into query. It wasn't placed inside it.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private boolean checkNull(Object object) {
        return object == null;
    }
}
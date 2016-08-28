package com.kolyadko.likeit.dao;


import com.kolyadko.likeit.entity.Entity;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionWrapper;
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
    protected ConnectionWrapper connection;
    protected static final Logger LOG = LogManager.getLogger(AbstractDao.class);

    public AbstractDao(ConnectionWrapper connection) {
        this.connection = connection;
    }

    public abstract T findById(K id) throws DaoException;

    public abstract ArrayList<T> findAll() throws DaoException;

    public abstract void create(T entity) throws DaoException;

    public abstract T readEntity(ResultSet resultSet) throws DaoException;

    protected void closeStatement(Statement statement) {
        if (!checkNull(statement)) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
    }

    protected ArrayList<T> findWithStatement(String query) throws DaoException {
        ArrayList<T> entities = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                entities.add(readEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
        }

        return entities;
    }

    protected ArrayList<T> findBy(String query, Object...params) throws DaoException {
        ArrayList<T> entities = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(query);
            int counter = 1;

            for (Object param : params) {
                if (!checkNull(param)) {
                    preparedStatement.setObject(counter, param);
                    counter++;
                } else {
                    LOG.warn("Null param was passed into query. It wasn't placed inside it.");
                }
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entities.add(readEntity(resultSet));
            }

            return entities;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
        }
    }

    protected T findOnlyOne(String query, Object param) throws DaoException {
        ArrayList<T> entities = findBy(query, param);

        if (!checkNull(entities) && !entities.isEmpty()) {
            return entities.get(0);
        }

        return null;
    }

    private boolean checkNull(Object object) {
        return object == null;
    }
}
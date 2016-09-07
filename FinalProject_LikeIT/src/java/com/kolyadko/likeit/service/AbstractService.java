package com.kolyadko.likeit.service;


import com.kolyadko.likeit.entity.Entity;
import com.kolyadko.likeit.exception.ConnectionPoolException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionPool;
import com.kolyadko.likeit.pool.ConnectionProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public abstract class AbstractService<K, T extends Entity> {
    protected static final Logger LOG = LogManager.getLogger(AbstractService.class);

    public abstract boolean create(T entity) throws ServiceException;

    protected ConnectionProxy getConnectionWrapper() throws ServiceException {
        ConnectionProxy connection;

        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new ServiceException(e);
        }

        return connection;
    }

    protected <T> T findBy(ConnectionProxy connection, String query, Object... params) throws ServiceException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int counter = 1;

            for (Object param : params) {
                if (!checkNull(param)) {
                    preparedStatement.setObject(counter, param);
                    counter++;
                } else {
                    throw new ServiceException("Null param was passed into query.");
                }
            }
            return extractData(connection, preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    protected <T> T extractData(ConnectionProxy connection, ResultSet resultSet) throws ServiceException {
        return null;
    }

    protected boolean checkNull(Object object) {
        return object == null;
    }
}
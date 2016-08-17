package com.kolyadko.likeit.service;


import com.kolyadko.likeit.entity.Entity;
import com.kolyadko.likeit.exception.ConnectionPoolException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionPool;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public abstract class AbstractService<K, T extends Entity> {
    protected static final Logger LOG = LogManager.getLogger(AbstractService.class);


    public abstract T findById(K id) throws ServiceException;

    public abstract void create(T entity) throws ServiceException;

    protected ConnectionWrapper getConnectionWrapper() throws ServiceException {
        ConnectionWrapper connection;

        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new ServiceException(e);
        }

        return connection;
    }

    protected void closeStatement(Statement statement) {
        if (!checkNull(statement)) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
    }

    private boolean checkNull(Object object) {
        return object == null;
    }
}
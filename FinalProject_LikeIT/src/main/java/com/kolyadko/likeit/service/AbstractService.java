package com.kolyadko.likeit.service;


import com.kolyadko.likeit.entity.Entity;
import com.kolyadko.likeit.exception.ConnectionPoolException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionPool;
import com.kolyadko.likeit.pool.ConnectionProxy;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public abstract class AbstractService<K, T extends Entity> {
    public abstract boolean create(T entity) throws ServiceException;

    protected ConnectionProxy getConnectionWrapper() throws ServiceException {
        ConnectionProxy connection;

        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new ServiceException("Exception while trying to get connection in service", e);
        }

        return connection;
    }

    protected boolean checkNull(Object object) {
        return object == null;
    }
}
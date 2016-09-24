package com.kolyadko.likeit.service;


import com.kolyadko.likeit.entity.Entity;
import com.kolyadko.likeit.exception.ConnectionPoolException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionPool;
import com.kolyadko.likeit.pool.ConnectionProxy;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */

/**
 * AbstractService class
 *
 * @param <K> entity's id type
 * @param <T> entity type
 */
public abstract class AbstractService<K, T extends Entity> {
    /**
     * Insert new entity into database
     *
     * @param entity entity to insert
     * @return true - inserted successfully<br>false - otherwise
     * @throws ServiceException if some problems occurred inside
     */
    public abstract boolean create(T entity) throws ServiceException;

    /**
     * Update entity in database
     *
     * @param entity entity to update
     * @return true - updated successfully<br>false - otherwise
     * @throws ServiceException if some problems occurred inside
     */
    public abstract boolean update(T entity) throws ServiceException;

    /**
     * Find entity by login
     *
     * @param id      entity id
     * @param isAdmin true - admin<br>false - general user
     * @return Entity object
     * @throws ServiceException if some problems occurred inside
     */
    public abstract T findById(K id, boolean isAdmin) throws ServiceException;

    /**
     * Move entity to archive
     *
     * @param id id
     * @return true - moved to archive successfully<br>false - otherwise
     * @throws ServiceException if some problems occurred inside
     */
    public abstract boolean moveToArchive(K id) throws ServiceException;

    /**
     * Restore entity from archive
     *
     * @param id id
     * @return true - restored successfully<br>false - otherwise
     * @throws ServiceException if some problems occurred inside
     */
    public abstract boolean restoreFromArchive(K id) throws ServiceException;

    /**
     * Get ConnectionProxy object from pool
     *
     * @return ConnectionProxy object
     * @throws ServiceException if some problems occurred inside
     */
    protected ConnectionProxy getConnectionProxy() throws ServiceException {
        ConnectionProxy connection;

        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new ServiceException("Exception while trying to get connection in service", e);
        }

        return connection;
    }
}
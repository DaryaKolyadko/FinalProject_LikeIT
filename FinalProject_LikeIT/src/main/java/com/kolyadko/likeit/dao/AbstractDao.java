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

/**
 * AbstractDao class
 *
 * @param <K> entity's id type
 * @param <T> entity type
 */
public abstract class AbstractDao<K, T extends Entity> {
    /**
     * Database connection (used with ConnectionProxy wrapper)
     * Logic layer
     */
    protected ConnectionProxy connection;
    protected static final Logger LOG = LogManager.getLogger(AbstractDao.class);

    public AbstractDao(ConnectionProxy connection) {
        this.connection = connection;
    }

    /**
     * Insert new entity into database
     *
     * @param entity entity to insert
     * @return true - inserted successfully<br>false - otherwise
     * @throws DaoException if some problems occurred inside
     */
    public abstract boolean create(T entity) throws DaoException;

    /**
     * Update entity in database
     *
     * @param entity entity to update
     * @return true - updated successfully<br>false - otherwise
     * @throws DaoException if some problems occurred inside
     */
    public abstract boolean update(T entity) throws DaoException;

    /**
     * Find entity by id
     *
     * @param id entity id
     * @return entity object
     * @throws DaoException if some problems occurred inside
     */
    public abstract T findById(K id) throws DaoException;

    /**
     * Find entity which is not in archive by id
     *
     * @param id entity id
     * @return entity object
     * @throws DaoException if some problems occurred inside
     */
    public abstract T findExistingById(K id) throws DaoException;

    /**
     * Move entity to archive\ restore entity from archive
     *
     * @param archive true - move to<br>false - restore
     * @param id   entity id
     * @return true - updated successfully<br>false - otherwise
     * @throws DaoException if some problems occurred inside
     */
    public abstract boolean archiveActionById(Boolean archive, K id) throws DaoException;

    /**
     * Retrieve data from ResultSrt object and init entity object
     *
     * @param resultSet result set object
     * @return initialized entity
     * @throws DaoException if some problems occurred inside
     */
    public abstract T readEntity(ResultSet resultSet) throws DaoException;

    /**
     * Execute SELECT SQL request (with no params, Statement) and return ArrayList of found entities
     *
     * @param query SQL-query
     * @return ArrayLst of found entities
     * @throws DaoException if some problems occurred inside
     */
    protected ArrayList<T> findWithStatement(String query) throws DaoException {
        ArrayList<T> entities = new ArrayList<>();
        ResultSet resultSet;

        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                entities.add(readEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in DAO layer, findWithStatement()", e);
        }

        return entities;
    }

    /**
     * Execute SELECT SQL request (with params, PreparedStatement) and return ArrayList of found entities
     *
     * @param query  SQL-query
     * @param params query param values
     * @return ArrayLst of found entities
     * @throws DaoException if some problems occurred inside
     */
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
            throw new DaoException("Exception in DAO layer, findBy()", e);
        }
    }

    /**
     * Execute SELECT SQL request (with params, PreparedStatement) and return found data as object
     *
     * @param query  SQL-query
     * @param params query param values
     * @param <T>    some data type (usually inner class in particular DAO)
     * @return some data (usually inner class in particular DAO)
     * @throws DaoException if some problems occurred inside
     */
    protected <T> T findDataBy(String query, Object... params) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int counter = 1;

            for (Object param : params) {
                if (!checkNull(param)) {
                    preparedStatement.setObject(counter, param);
                    counter++;
                } else {
                    throw new DaoException("Null param was passed into query.");
                }
            }
            return extractData(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Exception in DAO layer, findDataBy()", e);
        }
    }

    /**
     * Extract data from ResultSet object inside some other object (usually inner class in particular DAO)
     *
     * @param resultSet ResultSet object
     * @param <T>       some data type (usually inner class in particular DAO)
     * @return some data (usually inner class in particular DAO)
     * @throws DaoException if some problems occurred inside
     */
    protected <T> T extractData(ResultSet resultSet) throws DaoException {
        return null;
    }

    /**
     * Execute SELECT SQL request (with params, PreparedStatement) and return one found entity
     *
     * @param query SQL-query
     * @param param query param values
     * @return found entity
     * @throws DaoException if some problems occurred inside
     */
    protected T findOnlyOne(String query, Object param) throws DaoException {
        ArrayList<T> entities = findBy(query, param);

        if (!checkNull(entities) && !entities.isEmpty()) {
            return entities.get(0);
        }

        return null;
    }

    /**
     * Execute UPDATE SQL query (with params, PreparedStatement)
     *
     * @param query  SQL-query
     * @param params query params
     * @return true - updated successfully<br>false - otherwise
     * @throws DaoException if some problems occurred inside
     */
    protected boolean updateEntityWithQuery(String query, Object... params) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParams(preparedStatement, params);
            preparedStatement.executeUpdate();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e) {
            throw new DaoException("Exception in DAO layer, updateEntityWithQuery()", e);
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
            throw new DaoException("Exception in DAO layer, setParams()", e);
        }
    }

    /**
     * Check if object which was passed as param is null
     *
     * @param object object to check
     * @return true - is null<br>false - otherwise
     */
    protected boolean checkNull(Object object) {
        return object == null;
    }

    /**
     * PagerUtil for optimal data extracting
     */
    protected static class PagerUtil {
        private static final int NO_PAGES = -1;
        private static final String ROW_COUNT = "SELECT FOUND_ROWS()";

        private int itemsPerPage;

        public PagerUtil(int itemsPerPage) {
            this.itemsPerPage = itemsPerPage;
        }

        /**
         * Calculate object number which need to pass
         *
         * @param page page number
         * @return number of objects to pass
         */
        public int calculateListOffset(int page) {
            return (page - 1) * itemsPerPage;
        }

        /**
         * Calculate actual number of pages with data
         *
         * @param connection ConnectionProxy object
         * @return number of actual pages
         * @throws DaoException if some problems occurred inside
         */
        public int calculatePagesNumber(ConnectionProxy connection) throws DaoException {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(ROW_COUNT);

                if (resultSet.next()) {
                    int rowsNum = resultSet.getInt(1);
                    return calculatePageNumber(rowsNum);
                }

                return NO_PAGES;
            } catch (SQLException e) {
                throw new DaoException("Exception in DAO layer, calculatePagesNumber()", e);
            }
        }

        private int calculatePageNumber(double rowsNumber) {
            return (int) Math.ceil(rowsNumber / itemsPerPage);
        }
    }
}
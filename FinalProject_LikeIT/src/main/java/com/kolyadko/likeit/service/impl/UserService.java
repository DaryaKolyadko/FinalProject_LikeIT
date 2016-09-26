package com.kolyadko.likeit.service.impl;


import com.kolyadko.likeit.dao.impl.UserDao;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import com.kolyadko.likeit.service.AbstractService;
import com.kolyadko.likeit.type.StateType;
import com.kolyadko.likeit.util.HashUtil;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */

/**
 * This Service allows perform operations on database with users
 */
public class UserService extends AbstractService<String, User> {
    private static final Calendar CALENDAR = Calendar.getInstance();

    @Override
    public User findById(String id, Boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            UserDao userDao = new UserDao(connection);
            return isAdmin ? userDao.findById(id) : userDao.findExistingById(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in UserService, findById()", e);
        }
    }

    /**
     * Check if particular login is in use
     *
     * @param id user login
     * @return true - in use<br>false - available
     * @throws ServiceException if some problems occurred inside
     */
    public boolean isLoginInUse(String id) throws ServiceException {
        return findById(id, true) != null;
    }

    /**
     * Find users (all info, Wrapper is used)
     *
     * @param page    page number
     * @param isAdmin true - admin<br>false - general user
     * @return UserListWrapper object
     * @throws ServiceException if some problems occurred inside
     */
    public UserDao.UserListWrapper findAllUsers(Integer page, Boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            UserDao userDao = new UserDao(connection);
            return userDao.findAllUsers(page, isAdmin);
        } catch (DaoException e) {
            throw new ServiceException("Exception in UserService, findAllUsers()", e);
        }
    }

    @Override
    public boolean update(User user) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            UserDao userDao = new UserDao(connection);
            return userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Exception in UserService, updateUser()", e);
        }
    }

    @Override
    public boolean moveToArchive(String login) throws ServiceException {
        return archiveActionsById(true, login);
    }

    @Override
    public boolean restoreFromArchive(String login) throws ServiceException {
        return archiveActionsById(false, login);
    }

    private boolean archiveActionsById(Boolean archive, String login) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            UserDao userDao = new UserDao(connection);
            return userDao.archiveActionById(archive, login);
        } catch (DaoException e) {
            throw new ServiceException("Exception in UserService, archiveActionsById()", e);
        }
    }

    /**
     * Activate user profile
     *
     * @param login user login
     * @return true - activated profile successfully<br>false - otherwise
     * @throws ServiceException if some problems occurred inside
     */
    public boolean activateProfileById(String login) throws ServiceException {
        return stateActionsById(StateType.ACTIVE, login);
    }

    /**
     * Ban user profile
     *
     * @param login user login
     * @return true - banned profile successfully<br>false - otherwise
     * @throws ServiceException if some problems occurred inside
     */
    public boolean banProfileById(String login) throws ServiceException {
        return stateActionsById(StateType.BANNED, login);
    }

    private boolean stateActionsById(StateType state, String login) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            UserDao userDao = new UserDao(connection);
            return userDao.stateActionById(state, login);
        } catch (DaoException e) {
            throw new ServiceException("Exception in UserService, stateActionsById()", e);
        }
    }

    /**
     * Find user with credentials
     *
     * @param login    user login
     * @param password user password
     * @return User object
     * @throws ServiceException if some problems occurred inside
     */
    public User findUserWithCredentials(String login, String password) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            UserDao userDao = new UserDao(connection);
            User user = userDao.findExistingById(login);

            if (user != null && user.getPassword().equals(HashUtil.getHash(password))) {
                return user;
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in UserService, findUserWithCredentials()", e);
        }

        return null;
    }

    @Override
    public boolean create(User user) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            UserDao userDao = new UserDao(connection);
            user.setPassword(HashUtil.getHash(user.getPassword()));
            user.setSignUpDate(new Date(CALENDAR.getTime().getTime()));
            return userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException("Exception in UserService, create()", e);
        }
    }
}
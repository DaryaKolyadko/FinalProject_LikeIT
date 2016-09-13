package com.kolyadko.likeit.service.impl;


import com.kolyadko.likeit.dao.impl.UserDao;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import com.kolyadko.likeit.service.AbstractService;
import com.kolyadko.likeit.type.StateType;
import com.kolyadko.likeit.util.HashUtil;
import com.kolyadko.likeit.util.PagerUtil;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public class UserService extends AbstractService<String, User> {
    private static final Calendar CALENDAR = Calendar.getInstance();

    public User findById(String id, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            UserDao userDao = new UserDao(connection);
            return isAdmin ? userDao.findById(id) : userDao.findExistingById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isLoginInUse(String id) throws ServiceException {
        return findById(id, true) != null;
    }

    public UserDao.UserListWrapper findAllUsers(Integer page, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            UserDao userDao = new UserDao(connection);
            return userDao.findAllUsers(page, isAdmin);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean updateUser(User user) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            UserDao userDao = new UserDao(connection);
            return userDao.updateUser(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean moveProfileToArchive(String login) throws ServiceException {
        return archiveActionsById(true, login);
    }

    public boolean restoreProfileFromArchive(String login) throws ServiceException {
        return archiveActionsById(false, login);
    }

    private boolean archiveActionsById(boolean archive, String login) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            UserDao userDao = new UserDao(connection);
            return userDao.archiveActionById(archive, login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean activateProfileById(String login) throws ServiceException {
        return stateActionsById(StateType.ACTIVE, login);
    }

    public boolean banProfileById(String login) throws ServiceException {
        return stateActionsById(StateType.BANNED, login);
    }

    private boolean stateActionsById(StateType state, String login) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            UserDao userDao = new UserDao(connection);
            return userDao.stateActionById(state, login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User findUserWithCredentials(String login, String password) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            UserDao userDao = new UserDao(connection);
            User user = userDao.findExistingById(login);

            if (user != null && user.getPassword().equals(HashUtil.getHash(password))) {
                return user;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return null;
    }

    @Override
    public boolean create(User user) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            UserDao userDao = new UserDao(connection);
            user.setPassword(HashUtil.getHash(user.getPassword()));
            user.setSignUpDate(new Date(CALENDAR.getTime().getTime()));
            return userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
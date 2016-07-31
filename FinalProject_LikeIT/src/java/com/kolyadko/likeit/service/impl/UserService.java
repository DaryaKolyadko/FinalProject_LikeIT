package com.kolyadko.likeit.service.impl;


import com.kolyadko.likeit.dao.impl.UserDao;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import com.kolyadko.likeit.service.AbstractService;
import com.kolyadko.likeit.util.HashUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public class UserService extends AbstractService<String, User> {
    private static final Calendar CALENDAR = Calendar.getInstance();

    public User findById(String id) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        UserDao userDao = new UserDao(connection);

        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    public ArrayList<User> findAll(boolean isAdmin) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        UserDao userDao = new UserDao(connection);

        try {
            if (isAdmin) {
                return userDao.findAll();
            } else {
                return userDao.findAllExisting();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    @Override
    public void create(User user) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        UserDao userDao = new UserDao(connection);

        try {
            user.setPassword(HashUtil.getHash(user.getPassword()));
            user.setSignUpDate(new Date(CALENDAR.getTime().getTime()));
            userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    public User findUserWithCredentials(String login, String password) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        UserDao userDao = new UserDao(connection);

        try {
            User user = userDao.findById(login);

            if (user != null && user.getPassword().equals(HashUtil.getHash(password))) {
                return user;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }

        return null;
    }
}
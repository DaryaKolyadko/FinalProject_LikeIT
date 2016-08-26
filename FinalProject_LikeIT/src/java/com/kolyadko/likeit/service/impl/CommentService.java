package com.kolyadko.likeit.service.impl;

import com.kolyadko.likeit.dao.impl.CommentDao;
import com.kolyadko.likeit.dao.impl.QuestionDao;
import com.kolyadko.likeit.entity.Comment;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import com.kolyadko.likeit.service.AbstractService;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class CommentService extends AbstractService<Integer, Comment> {
    @Override
    public Comment findById(Integer id) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        CommentDao commentDao = new CommentDao(connection);

        try {
            return commentDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    @Override
    public void create(Comment comment) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        CommentDao commentDao = new CommentDao(connection);

        try {
            commentDao.create(comment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    public void findCommentsByAuthorId(String login) {
        // TODO
    }
}
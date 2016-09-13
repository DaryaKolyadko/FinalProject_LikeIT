package com.kolyadko.likeit.service.impl;

import com.kolyadko.likeit.dao.impl.CommentDao;
import com.kolyadko.likeit.entity.Comment;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import com.kolyadko.likeit.service.AbstractService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class CommentService extends AbstractService<Integer, Comment> {
    private static final Calendar CALENDAR = Calendar.getInstance();

    public boolean setCommentMark(int commentId, String userId, int mark) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            CommentDao commentDao = new CommentDao(connection);
            return commentDao.setCommentMark(commentId, userId, mark);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean noteAsAnswer(int commentId, boolean state) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            CommentDao commentDao = new CommentDao(connection);
            return commentDao.noteAsAnswer(commentId, state);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean create(Comment comment) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            CommentDao commentDao = new CommentDao(connection);
            comment.setCreationDate(new Timestamp(CALENDAR.getTime().getTime()));
            return commentDao.create(comment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<CommentDao.CommentData> findByQuestionId(Integer questionId, String login, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            CommentDao commentDao = new CommentDao(connection);
            return isAdmin ? commentDao.findByQuestionId(questionId, login, isAdmin) :
                    commentDao.findByQuestionId(questionId, login, isAdmin);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Comment findById(Integer commentId, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            CommentDao commentDao = new CommentDao(connection);
            return isAdmin ? commentDao.findById(commentId) : commentDao.findExistingById(commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Comment findById(Integer commentId) throws ServiceException {
        return findById(commentId, false);
    }


    public boolean updateComment(Comment comment) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            CommentDao commentDao = new CommentDao(connection);
            return commentDao.updateComment(comment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean moveCommentToArchive(int commentId) throws ServiceException {
        return archiveActionsById(true, commentId);
    }

    public boolean restoreCommentFromArchive(int commentId) throws ServiceException {
        return archiveActionsById(false, commentId);
    }

    private boolean archiveActionsById(boolean archive, int commentId) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            CommentDao commentDao = new CommentDao(connection);
            return commentDao.archiveActionById(archive, commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
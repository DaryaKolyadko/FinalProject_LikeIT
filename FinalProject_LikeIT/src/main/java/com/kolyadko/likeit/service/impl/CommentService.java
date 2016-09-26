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

/**
 * This Service allows perform operations on database with comments
 */
public class CommentService extends AbstractService<Long, Comment> {
    private static final Calendar CALENDAR = Calendar.getInstance();

    /**
     * Set comment mark
     *
     * @param commentId comment id
     * @param userId    user login
     * @param mark      mark that user has set
     * @return true - updated successfully<br>false - otherwise
     * @throws ServiceException if some problems occurred inside
     */
    public boolean setCommentMark(Long commentId, String userId, Integer mark) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            CommentDao commentDao = new CommentDao(connection);
            return commentDao.setCommentMark(commentId, userId, mark);
        } catch (DaoException e) {
            throw new ServiceException("Exception in CommentService, setCommentMark()", e);
        }
    }

    /**
     * Note comment as answer
     *
     * @param commentId comment id
     * @param state     true - comment is a right answer<br>false - otherwise
     * @return true - updated successfully<br>false - otherwise
     * @throws ServiceException if some problems occurred inside
     */
    public boolean noteAsAnswer(Long commentId, Boolean state) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            CommentDao commentDao = new CommentDao(connection);
            return commentDao.noteAsAnswer(commentId, state);
        } catch (DaoException e) {
            throw new ServiceException("Exception in CommentService, noteAsAnswer()", e);
        }
    }

    @Override
    public boolean create(Comment comment) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            CommentDao commentDao = new CommentDao(connection);
            comment.setCreationDate(new Timestamp(CALENDAR.getTime().getTime()));
            return commentDao.create(comment);
        } catch (DaoException e) {
            throw new ServiceException("Exception in CommentService, create()", e);
        }
    }

    /**
     * Find CommentDao.CommentData list by question id
     *
     * @param questionId question id
     * @param login      user login
     * @param isAdmin    true - admin<br>false - general user
     * @return CommentDao.CommentData list
     * @throws ServiceException if some problems occurred inside
     */
    public ArrayList<CommentDao.CommentData> findByQuestionId(Long questionId, String login,
                                                              Boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            CommentDao commentDao = new CommentDao(connection);
            return isAdmin ? commentDao.findByQuestionId(questionId, login, isAdmin) :
                    commentDao.findByQuestionId(questionId, login, isAdmin);
        } catch (DaoException e) {
            throw new ServiceException("Exception in CommentService, findByQuestionId()", e);
        }
    }

    @Override
    public Comment findById(Long commentId, Boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            CommentDao commentDao = new CommentDao(connection);
            return isAdmin ? commentDao.findById(commentId) : commentDao.findExistingById(commentId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in CommentService, findById()", e);
        }
    }

    /**
     * Find comment by id (as general user)
     *
     * @param commentId comment id
     * @return Comment object
     * @throws ServiceException if some problems occurred inside
     */
    public Comment findById(Long commentId) throws ServiceException {
        return findById(commentId, false);
    }

    @Override
    public boolean update(Comment comment) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            CommentDao commentDao = new CommentDao(connection);
            return commentDao.update(comment);
        } catch (DaoException e) {
            throw new ServiceException("Exception in CommentService, updateComment()", e);
        }
    }

    @Override
    public boolean moveToArchive(Long commentId) throws ServiceException {
        return archiveActionsById(true, commentId);
    }

    @Override
    public boolean restoreFromArchive(Long commentId) throws ServiceException {
        return archiveActionsById(false, commentId);
    }

    private boolean archiveActionsById(Boolean archive, Long commentId) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            CommentDao commentDao = new CommentDao(connection);
            return commentDao.archiveActionById(archive, commentId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in CommentService, archiveActionsById()", e);
        }
    }
}
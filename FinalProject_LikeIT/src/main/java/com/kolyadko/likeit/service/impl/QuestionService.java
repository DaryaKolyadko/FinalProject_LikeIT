package com.kolyadko.likeit.service.impl;

import com.kolyadko.likeit.dao.impl.QuestionDao;
import com.kolyadko.likeit.entity.Question;
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
 * This Service allows perform operations on database with questions
 */
public class QuestionService extends AbstractService<Integer, Question> {
    private static final Calendar CALENDAR = Calendar.getInstance();

    @Override
    public boolean create(Question question) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            question.setCreationDate(new Timestamp(CALENDAR.getTime().getTime()));
            return questionDao.create(question);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, create()", e);
        }
    }

    /**
     * Set question mark from particular user
     *
     * @param questionId question id
     * @param userId     user id
     * @param mark       mark that user has set
     * @return true - updated successfully<br>false - otherwise
     * @throws ServiceException if some problems occurred inside
     */
    public boolean setQuestionMark(int questionId, String userId, int mark) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.setQuestionMark(questionId, userId, mark);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, setQuestionMark()", e);
        }
    }

    @Override
    public Question findById(Integer questionId, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return isAdmin ? questionDao.findById(questionId) : questionDao.findExistingById(questionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findById()", e);
        }
    }

    /**
     * Find question by id (as general user)
     *
     * @param questionId question id
     * @return Question object
     * @throws ServiceException if some problems occurred inside
     */
    public Question findById(int questionId) throws ServiceException {
        return findById(questionId, false);
    }

    /**
     * Find recent questions
     *
     * @param page page number
     * @return QuestionDao.QuestionListWrapper object
     * @throws ServiceException if some problems occurred inside
     */
    public QuestionDao.QuestionListWrapper findRecentQuestions(int page) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findRecentQuestions(page);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findRecentQuestions()", e);
        }
    }

    /**
     * Find top questions with limit (is used in right side menu)
     *
     * @param limit max questions number
     * @return question list
     * @throws ServiceException if some problems occurred inside
     */
    public ArrayList<Question> findTopQuestions(int limit) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findTopQuestions(limit);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findTopQuestions()", e);
        }
    }

    /**
     * Find top questions (on page)
     *
     * @param page page number
     * @return QuestionDao.QuestionListWrapper object
     * @throws ServiceException if some problems occurred inside
     */
    public QuestionDao.QuestionListWrapper findTopQuestionsOnPage(int page) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findTopQuestionsOnPage(page);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findTopQuestionsOnPage()", e);
        }
    }

    /**
     * Find questions from particular section
     *
     * @param sectionId section id
     * @param page      page number
     * @param isAdmin   true - admin<br>false - general user
     * @return QuestionDao.QuestionListWrapper object
     * @throws ServiceException if some problems occurred inside
     */
    public QuestionDao.QuestionListWrapper findQuestionsFromSection(int sectionId, int page, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findQuestionsFromSection(sectionId, page, isAdmin);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findQuestionsFromSection()", e);
        }
    }

    @Override
    public boolean moveToArchive(Integer questionId) throws ServiceException {
        return archiveActionsById(true, questionId);
    }

    @Override
    public boolean restoreFromArchive(Integer questionId) throws ServiceException {
        return archiveActionsById(false, questionId);
    }

    private boolean archiveActionsById(boolean archive, int questionId) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.archiveActionById(archive, questionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, archiveActionsById()", e);
        }
    }

    /**
     * Find QuestionData (question, author, section?, mark)
     *
     * @param questionId question id
     * @param isAdmin    true - admin<br>false - general user
     * @param login      current user login
     * @return QuestionDao.QuestionData object
     * @throws ServiceException if some problems occurred inside
     */
    public QuestionDao.QuestionData findQuestionData(int questionId, boolean isAdmin, String login) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findQuestionData(questionId, isAdmin, login);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findQuestionData()", e);
        }
    }

    @Override
    public boolean update(Question question) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.update(question);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, updateQuestion()", e);
        }
    }
}
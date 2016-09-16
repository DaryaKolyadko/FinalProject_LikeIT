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
public class QuestionService extends AbstractService<Integer, Question> {
//    private static final String JOIN_ON_SECTION_GENERAL = " JOIN section S ON Q.section_id = S.section_id JOIN section MS " +
//            "ON S.major_section_id = MS.section_id";
//    private static final String ALL_COLUMNS = "Q.question_id, Q.author_id, Q.section_id, Q.title, Q.text, Q.creation_date, " +
//            "Q.rating, Q.archive";
//    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM question Q";
//    private static final String DESC_ORDER_BY_RATING = " ORDER BY Q.rating DESC";
//    private static final String EXISTING = " WHERE Q.archive=false AND S.archive=false AND MS.archive=false";
//    private static final String LIMIT = " LIMIT ?";

    private static final Calendar CALENDAR = Calendar.getInstance();

    @Override
    public boolean create(Question question) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            question.setCreationDate(new Timestamp(CALENDAR.getTime().getTime()));
            return questionDao.create(question);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, create()", e);
        }
    }

    public boolean setQuestionMark(int questionId, String userId, int mark) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.setQuestionMark(questionId, userId, mark);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, setQuestionMark()", e);
        }
    }

    public Question findById(int questionId, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return isAdmin ? questionDao.findById(questionId) : questionDao.findExistingById(questionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findById()", e);
        }
    }

    public Question findById(int questionId) throws ServiceException {
        return findById(questionId, false);
    }

    public QuestionDao.QuestionListWrapper findRecentQuestions(int page) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findRecentQuestions(page);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findRecentQuestions()", e);
        }
    }

    public ArrayList<Question> findTopQuestions(int limit) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findTopQuestions(limit);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findTopQuestions()", e);
        }
    }

    public QuestionDao.QuestionListWrapper findTopQuestionsOnPage(int page) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findTopQuestionsOnPage(page);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findTopQuestionsOnPage()", e);
        }
    }

    public QuestionDao.QuestionListWrapper findQuestionsFromSection(int sectionId, int page, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findQuestionsFromSection(sectionId, page, isAdmin);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findQuestionsFromSection()", e);
        }
    }

    public boolean moveQuestionToArchive(int questionId) throws ServiceException {
        return archiveActionsById(true, questionId);
    }

    public boolean restoreQuestionFromArchive(int questionId) throws ServiceException {
        return archiveActionsById(false, questionId);
    }

    private boolean archiveActionsById(boolean archive, int questionId) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.archiveActionById(archive, questionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, archiveActionsById()", e);
        }
    }

    public QuestionDao.QuestionData findQuestionData(int questionId, boolean isAdmin, String login) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.findQuestionData(questionId, isAdmin, login);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, findQuestionData()", e);
        }
    }

    public boolean updateQuestion(Question question) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.updateQuestion(question);
        } catch (DaoException e) {
            throw new ServiceException("Exception in QuestionService, updateQuestion()", e);
        }
    }
}
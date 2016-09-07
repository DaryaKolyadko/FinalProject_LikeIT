package com.kolyadko.likeit.service.impl;

import com.kolyadko.likeit.dao.impl.QuestionDao;
import com.kolyadko.likeit.dao.impl.SectionDao;
import com.kolyadko.likeit.dao.impl.UserDao;
import com.kolyadko.likeit.entity.Question;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import com.kolyadko.likeit.service.AbstractService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class QuestionService extends AbstractService<Integer, Question> {
    private static final String SELECT_GENERAL = "SELECT SQL_CALC_FOUND_ROWS Q.question_id, author_id, Q.section_id, title, text, creation_date, Q.comment_num, " +
            "Q.answer_num, mark_num, Q.rating, Q.archive, login, avatar_id, first_name, last_name, gender, password, birth_date," +
            "sign_up_date, email, role, state, U.rating, U.answer_num, U.question_num, U.comment_num, U.archive, " +
            "S.section_id, S.major_section_id, S.name, S.question_num, S.answer_num, MS.label_color, S.archive";
    private static final String JOIN_ON_SECTION_GENERAL = " JOIN section S ON Q.section_id = S.section_id JOIN section MS " +
            "ON S.major_section_id = MS.section_id";
    private static final String JOIN_GENERAL = " JOIN user U ON Q.author_id = U.login" + JOIN_ON_SECTION_GENERAL;
    private static final String SELECT_QUEST_USER_SECT = SELECT_GENERAL + ", null as mark FROM question Q" + JOIN_GENERAL;
    private static final String SELECT_QUEST_USER_SECT_MARK = SELECT_GENERAL + ", ifnull(QR.mark,0) as mark  FROM question Q" +
            JOIN_GENERAL + " LEFT OUTER JOIN (SELECT question_id, mark FROM question_rating WHERE user_id=?) QR " +
            "ON Q.question_id=QR.question_id";
    private static final String ALL_COLUMNS = "Q.question_id, Q.author_id, Q.section_id, Q.title, Q.text, Q.creation_date, " +
            "Q.comment_num, Q.answer_num, Q.mark_num, Q.rating, Q.archive";
    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM question Q";
    private static final String ROW_COUNT = "SELECT FOUND_ROWS()";

    private static final String QUEST_ID = " Q.question_id=?";
    private static final String DESC_ORDER_BY_CREATE_DATE = " ORDER BY creation_date DESC";
    private static final String DESC_ORDER_BY_RATING = " ORDER BY Q.rating DESC";
    private static final String FROM_PARTICULAR_SECTION = " S.section_id=?";
    private static final String EXISTING = " WHERE Q.archive=false AND S.archive=false AND MS.archive=false";
    private static final String AND = " AND";
    private static final String WHERE = " WHERE";
    private static final String LIMIT = " LIMIT ?";
    private static final String OFFSET_AND_LIMIT = LIMIT + ", ?";

    private static final int RECORDS_PER_PAGE = 1;
    private static final int NO_PAGES = -1;

    private static final Calendar CALENDAR = Calendar.getInstance();

    @Override
    public boolean create(Question question) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            question.setCreationDate(new Timestamp(CALENDAR.getTime().getTime()));
            return questionDao.create(question);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Question findById(Integer questionId, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return isAdmin ? questionDao.findById(questionId) : questionDao.findExistingById(questionId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public QuestionData findQuestionData(int questionId, boolean isAdmin) throws ServiceException {
        return findOnlyOne(SELECT_QUEST_USER_SECT + (isAdmin ? WHERE : EXISTING + AND) + QUEST_ID, questionId);
    }

    public QuestionData findQuestionData(int questionId, boolean isAdmin, String login) throws ServiceException {
        if (login != null) {
            return findOnlyOne(SELECT_QUEST_USER_SECT_MARK + (isAdmin ? WHERE : EXISTING + AND) + QUEST_ID, login, questionId);
        }

        return findQuestionData(questionId, isAdmin);
    }

    public QuestionListWrapper findQuestionsFromSection(Integer sectionId, Integer page, boolean isAdmin)
            throws ServiceException {
        return findBy(SELECT_QUEST_USER_SECT +
                (isAdmin ? WHERE : (EXISTING + AND)) + FROM_PARTICULAR_SECTION + DESC_ORDER_BY_CREATE_DATE +
                OFFSET_AND_LIMIT, sectionId, calculateListOffset(page), RECORDS_PER_PAGE);
    }

    public QuestionListWrapper findRecentQuestions(Integer page) throws ServiceException {
        return findBy(SELECT_QUEST_USER_SECT + EXISTING + DESC_ORDER_BY_CREATE_DATE + OFFSET_AND_LIMIT,
                calculateListOffset(page), RECORDS_PER_PAGE);
    }

    public ArrayList<Question> findTopQuestions(int limit) throws ServiceException {
        ArrayList<Question> questions = new ArrayList<>();

        try (ConnectionProxy connection = getConnectionWrapper();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL +
                     JOIN_ON_SECTION_GENERAL + EXISTING + DESC_ORDER_BY_RATING + LIMIT)) {
            QuestionDao questionDao = new QuestionDao(connection);
            preparedStatement.setInt(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questions.add(questionDao.readEntity(resultSet));
            }

            return questions;
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    public QuestionListWrapper findTopQuestionsOnPage(int page) throws ServiceException {
        return findBy(SELECT_QUEST_USER_SECT + EXISTING + DESC_ORDER_BY_RATING + OFFSET_AND_LIMIT,
                calculateListOffset(page), RECORDS_PER_PAGE);
    }

    private QuestionData findOnlyOne(String query, Object... params) throws ServiceException {
        ArrayList<QuestionData> entities = findDataBy(query, params);

        if (!checkNull(entities) && !entities.isEmpty()) {
            return entities.get(0);
        }

        return null;
    }


    private ArrayList<QuestionData> findDataBy(String query, Object... params) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            return findBy(connection, query, params);
        }
    }

    private QuestionListWrapper findBy(String query, Object... params) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionListWrapper wrapper = new QuestionListWrapper();
            wrapper.setQuestionList(findBy(connection, query, params));
            wrapper.setPagesNumber(calculatePagesNumber(connection));
            return wrapper;
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
            throw new ServiceException(e);
        }
    }

    private int calculateListOffset(int page) {
        return (page - 1) * RECORDS_PER_PAGE;
    }

    private int calculatePagesNumber(ConnectionProxy connection) throws ServiceException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(ROW_COUNT);

            if (resultSet.next()) {
                int rowsNum = resultSet.getInt(1);
                return calculatePageNumber(rowsNum);
            }

            return NO_PAGES;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    private int calculatePageNumber(double rowsNumber) {
        return (int) Math.ceil(rowsNumber / RECORDS_PER_PAGE);
    }

    @Override
    protected ArrayList<QuestionData> extractData(ConnectionProxy connection, ResultSet resultSet) throws ServiceException {
        QuestionDao questionDao = new QuestionDao(connection);
        UserDao userDao = new UserDao(connection);
        SectionDao sectionDao = new SectionDao(connection);
        ArrayList<QuestionData> dataList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                QuestionData data = new QuestionData();
                data.setQuestion(questionDao.readEntity(resultSet));
                data.setUser(userDao.readEntity(resultSet));
                data.setSection(sectionDao.readEntity(resultSet));
                data.setMark(resultSet.getInt("mark"));
                dataList.add(data);
            }
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }

        return dataList;
    }

    public boolean updateQuestion(Question question) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            QuestionDao questionDao = new QuestionDao(connection);
            return questionDao.updateQuestion(question);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public class QuestionData {
        private Question question;
        private User user;
        private Section section;
        private Integer mark;

        public Question getQuestion() {
            return question;
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Section getSection() {
            return section;
        }

        public void setSection(Section section) {
            this.section = section;
        }

        public Integer getMark() {
            return mark;
        }

        public void setMark(Integer mark) {
            this.mark = mark;
        }
    }

    public class QuestionListWrapper {
        private ArrayList<QuestionData> questionList;
        private Integer pagesNumber;

        public QuestionListWrapper() {
            questionList = new ArrayList<>();
        }

        public List<QuestionData> getQuestionList() {
            return Collections.unmodifiableList(questionList);
        }

        public void setQuestionList(ArrayList<QuestionData> questionList) {
            this.questionList = questionList;
        }

        public Integer getPagesNumber() {
            return pagesNumber;
        }

        public void setPagesNumber(Integer pagesNumber) {
            this.pagesNumber = pagesNumber;
        }
    }
}
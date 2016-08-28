package com.kolyadko.likeit.service.impl;

import com.kolyadko.likeit.dao.impl.QuestionDao;
import com.kolyadko.likeit.dao.impl.SectionDao;
import com.kolyadko.likeit.dao.impl.UserDao;
import com.kolyadko.likeit.entity.Question;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import com.kolyadko.likeit.service.AbstractService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class QuestionService extends AbstractService<Integer, Question> {
    private static final String SELECT_GENERAL = "SELECT Q.question_id, author_id, Q.section_id, title, text, creation_date, version, last_modify, Q.comment_num, " +
            "Q.answer_num, mark_num, Q.rating, Q.archive, login, avatar_id, first_name, last_name, gender, password, birth_date," +
            "sign_up_date, email, email_confirmed, role, state, U.rating, U.answer_num, U.question_num, U.comment_num, U.archive, " +
            "S.section_id, S.major_section_id, S.name, S.question_num, S.answer_num, MS.label_color, S.archive";
    private static final String JOIN_GENERAL = "JOIN user U ON Q.author_id = U.login JOIN section S ON Q.section_id = S.section_id JOIN section MS " +
            "ON S.major_section_id = MS.section_id";
    private static final String SELECT_QUEST_USER_SECT = SELECT_GENERAL + ", null as mark FROM question Q " + JOIN_GENERAL;
    private static final String SELECT_QUEST_USER_SECT_MARK = SELECT_GENERAL + ", ifnull(QR.mark,0) as mark  FROM question Q " +
            JOIN_GENERAL + " LEFT OUTER JOIN (SELECT question_id, mark FROM question_rating WHERE user_id=?) QR " +
            "ON Q.question_id=QR.question_id";
    private static final String ALL_COLUMNS = "question_id, author_id, section_id, title, text, creation_date, " +
            "version, last_modify, comment_num, answer_num, mark_num, rating, archive";
    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM question Q";

    private static final String QUEST_ID = "  AND Q.question_id=?";

    private static final String DESC_ORDER_BY_CREATE_DATE = " ORDER BY creation_date DESC";
    private static final String DESC_ORDER_BY_RATING = " ORDER BY Q.rating DESC";
    private static final String FROM_PARTICULAR_SECTION = " S.section_id=?";
    private static final String EXISTING = " WHERE Q.archive=false";
    private static final String AND = " AND";
    private static final String WHERE = " WHERE";
    private static final String LIMIT = " LIMIT ?";

    @Override
    public Question findById(Integer id) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        QuestionDao questionDao = new QuestionDao(connection);

        try {
            return questionDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    @Override
    public void create(Question question) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        QuestionDao questionDao = new QuestionDao(connection);

        try {
            questionDao.create(question);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    public QuestionData findQuestionData(int questionId) throws ServiceException {
        return findOnlyOne(SELECT_QUEST_USER_SECT + EXISTING + QUEST_ID, questionId);
    }

    public QuestionData findQuestionData(int questionId, String login) throws ServiceException {
        if (login != null) {
            return findOnlyOne(SELECT_QUEST_USER_SECT_MARK + EXISTING + QUEST_ID, login, questionId);
        }

        return findQuestionData(questionId);
    }

    public ArrayList<QuestionData> findQuestionsFromSection(Integer sectionId, boolean isAdmin) throws ServiceException {
        return findBy(SELECT_QUEST_USER_SECT +
                (isAdmin ? WHERE : (EXISTING + AND)) + FROM_PARTICULAR_SECTION
                + DESC_ORDER_BY_CREATE_DATE, sectionId);
    }

    public ArrayList<QuestionData> findRecentQuestions() throws ServiceException {
        return findData(SELECT_QUEST_USER_SECT + EXISTING +
                DESC_ORDER_BY_CREATE_DATE);
    }

    public ArrayList<Question> findTopQuestions(int limit) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        QuestionDao questionDao = new QuestionDao(connection);
        PreparedStatement preparedStatement = null;
        ArrayList<Question> questions = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL + EXISTING + DESC_ORDER_BY_RATING + LIMIT);
            preparedStatement.setInt(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questions.add(questionDao.readEntity(resultSet));
            }

            return questions;
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        } finally {
            closeStatement(preparedStatement);
            connection.close();
        }
    }

    public ArrayList<QuestionData> findTopQuestions() throws ServiceException {
        return findData(SELECT_QUEST_USER_SECT + EXISTING + DESC_ORDER_BY_RATING);
    }

    private QuestionData findOnlyOne(String query, Object... params) throws ServiceException {
        ArrayList<QuestionData> entities = findBy(query, params);

        if (!checkNull(entities) && !entities.isEmpty()) {
            return entities.get(0);
        }

        return null;
    }

    private ArrayList<QuestionData> findBy(String query, Object... params) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            int counter = 1;

            for (Object param : params) {
                if (!checkNull(param)) {
                    preparedStatement.setObject(counter, param);
                    counter++;
                } else {
                    LOG.warn("Null param was passed into query. It wasn't placed inside it.");
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            return extractData(connection, resultSet);
        } catch (SQLException e) {
            throw new ServiceException(e);
        } finally {
            closeStatement(preparedStatement);
            connection.close();
        }
    }

    private ArrayList<QuestionData> findData(String query) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return extractData(connection, resultSet);
        } catch (SQLException e) {
            throw new ServiceException(e);
        } finally {
            closeStatement(statement);
            connection.close();
        }
    }

    private ArrayList<QuestionData> extractData(ConnectionWrapper connection, ResultSet resultSet) throws ServiceException {
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
}
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
import java.util.Collections;
import java.util.List;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class QuestionService extends AbstractService<Integer, Question> {
    private static final String SELECT_QUEST_USER_SECT = "SELECT question_id, author_id, Q.section_id, title, text, creation_date, version, last_modify, Q.comment_num, " +
            "Q.answer_num, mark_num, Q.rating, Q.archive, login, avatar_id, first_name, last_name, gender, password, birth_date," +
            "sign_up_date, email, email_confirmed, role, state, U.rating, U.answer_num, U.question_num, U.comment_num, U.archive, " +
            "S.section_id, S.major_section_id, S.name, S.question_num, S.answer_num, MS.label_color, S.archive " +
            "FROM question Q JOIN user U ON Q.author_id = U.login join section S ON Q.section_id = S.section_id JOIN section MS " +
            "ON S.major_section_id = MS.section_id";
    private static final String ALL_COLUMNS = "question_id, author_id, section_id, title, text, creation_date, " +
            "version, last_modify, comment_num, answer_num, mark_num, rating, archive";
    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM question Q";

    private static final String DESC_ORDER_BY_CREATE_DATE = " ORDER BY creation_date DESC";
    private static final String DESC_ORDER_BY_RATING = "  ORDER BY Q.rating DESC";
    private static final String FROM_PARTICULAR_SECTION = " S.name=?";
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

    public QuestionListData findQuestionsFromSection(String sectionName, boolean isAdmin) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT_QUEST_USER_SECT +
                    (isAdmin ? WHERE : (EXISTING + AND))+ FROM_PARTICULAR_SECTION
                    + DESC_ORDER_BY_CREATE_DATE);
            preparedStatement.setString(1, sectionName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractData(connection, resultSet);
        } catch (SQLException e) {
            throw new ServiceException(e);
        } finally {
            closeStatement(preparedStatement);
            connection.close();
        }
    }

    public QuestionListData findRecentQuestions() throws ServiceException {
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

    public QuestionListData findTopQuestions() throws ServiceException {
        return findData(SELECT_QUEST_USER_SECT + EXISTING + DESC_ORDER_BY_RATING);
    }

    private QuestionListData findData(String query) throws ServiceException {
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

    private QuestionListData extractData(ConnectionWrapper connection, ResultSet resultSet) throws ServiceException {
        QuestionDao questionDao = new QuestionDao(connection);
        UserDao userDao = new UserDao(connection);
        SectionDao sectionDao = new SectionDao(connection);
        QuestionListData data = new QuestionListData();

        try {
            while (resultSet.next()) {
                data.addQuestion(questionDao.readEntity(resultSet));
                data.addUser(userDao.readEntity(resultSet));
                data.addSection(sectionDao.readEntity(resultSet));
            }
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }

        return data;
    }

    public class QuestionListData {
        private ArrayList<Question> questions;
        private ArrayList<User> users;
        private ArrayList<Section> sections;

        public QuestionListData() {
            questions = new ArrayList<>();
            users = new ArrayList<>();
            sections = new ArrayList<>();
        }

        public void addQuestion(Question question) {
            questions.add(question);
        }

        public void addUser(User user) {
            users.add(user);
        }

        public void addSection(Section section) {
            sections.add(section);
        }

        public List<Question> getQuestions() {
            return Collections.unmodifiableList(questions);
        }

        public List<User> getUsers() {
            return Collections.unmodifiableList(users);
        }

        public List<Section> getSections() {
            return Collections.unmodifiableList(sections);
        }
    }

//    public class QuestionsFromSectionData {
//        private ArrayList<Question> questions;
//        private ArrayList<User> users;
//        private Section section;
//
//        public QuestionsFromSectionData() {
//            questions = new ArrayList<>();
//            users = new ArrayList<>();
//            section = new Section();
//        }
//
//        public void addQuestion(Question question) {
//            questions.add(question);
//        }
//
//        public void addUser(User user) {
//            users.add(user);
//        }
//
//        public void setSection(Section section) {
//            this.section = section;
//        }
//
//        public List<Question> getQuestions() {
//            return Collections.unmodifiableList(questions);
//        }
//
//        public List<User> getUsers() {
//            return Collections.unmodifiableList(users);
//        }
//
//        public Section getSection() {
//            return section;
//        }
//    }
}
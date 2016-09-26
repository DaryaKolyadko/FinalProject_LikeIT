package com.kolyadko.likeit.dao.impl;

import com.kolyadko.likeit.dao.AbstractDao;
import com.kolyadko.likeit.entity.Question;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */

/**
 * This DAO allows perform operations on database with questions
 */
public class QuestionDao extends AbstractDao<Long, Question> {
    private static final String INSERT_COLUMNS = "author_id, section_id, title, text, creation_date";
    private static final String CREATE = "INSERT INTO question (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";
    private static final String WHERE_ID = " WHERE question_id=?";
    private static final String ARCHIVE_ACTIONS = "UPDATE question SET archive=?" + WHERE_ID;
    private static final String ALL_COLUMNS = "Q.question_id, Q.author_id, Q.section_id, Q.title, Q.text, Q.creation_date, " +
            "Q.rating, Q.archive";
    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM question Q";
    private static final String SELECT_GENERAL = "SELECT SQL_CALC_FOUND_ROWS Q.question_id, author_id, Q.section_id, title, text, creation_date, " +
            "Q.rating, Q.archive, login, first_name, last_name, gender, password, birth_date," +
            "sign_up_date, email, role, state, U.rating, U.archive, " +
            "S.section_id, S.major_section_id, S.name, S.question_num, S.answer_num, MS.label_color, S.archive";
    private static final String JOIN_ON_SECTION_GENERAL = " JOIN section S ON Q.section_id = S.section_id JOIN section MS " +
            "ON S.major_section_id = MS.section_id";
    private static final String JOIN_GENERAL = " JOIN user U ON Q.author_id = U.login" + JOIN_ON_SECTION_GENERAL;
    private static final String SELECT_QUEST_USER_SECT = SELECT_GENERAL + ", null as mark FROM question Q" + JOIN_GENERAL;
    private static final String SELECT_QUEST_USER_SECT_MARK = SELECT_GENERAL + ", ifnull(QR.mark,0) as mark  FROM question Q" +
            JOIN_GENERAL + " LEFT OUTER JOIN (SELECT question_id, mark FROM question_rating WHERE user_id=?) QR " +
            "ON Q.question_id=QR.question_id";
    private static final String EXISTING = " Q.archive=false AND S.archive=false AND MS.archive=false";
    private static final String AND = " AND";
    private static final String WHERE = " WHERE";
    private static final String UPDATE_COLUMNS = "section_id, title, text";
    private static final String UPDATE_QUESTION = "UPDATE question SET " + String.join("=?, ", UPDATE_COLUMNS.split(",")) + "=?";
    private static final String QUEST_ID = " Q.question_id=?";
    private static final String DESC_ORDER_BY_CREATE_DATE = " ORDER BY creation_date DESC";
    private static final String DESC_ORDER_BY_RATING = " ORDER BY Q.rating DESC";
    private static final String FROM_PARTICULAR_SECTION = " S.section_id=?";
    private static final String LIMIT = " LIMIT ?";
    private static final String OFFSET_AND_LIMIT = LIMIT + ", ?";
    private static final int RECORDS_PER_PAGE = 2;

    // M-M relation (question_rating table)
    private static final int MARK_EXISTS = 1;
    private static final String ALL_RELATION_COLUMNS = "question_id, user_id, mark";
    private static final String WHERE_RELATION_ID = " WHERE question_id=? AND user_id=?";
    private static final String SELECT_NUM_OF_MARKS = "SELECT COUNT(*) FROM question_rating";
    private static final String UPDATE_QUESTION_MARK = "UPDATE question_rating SET mark=?";
    private static final String INSERT_QUESTION_MARK = "INSERT INTO question_rating (" + ALL_RELATION_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", ALL_RELATION_COLUMNS.split(",").length) + ");";

    private static final PagerUtil pager = new PagerUtil(RECORDS_PER_PAGE);

    public QuestionDao(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public Question findById(Long id) throws DaoException {
        return findOnlyOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public Question findExistingById(Long id) throws DaoException {
        return findOnlyOne(SELECT_ALL + JOIN_ON_SECTION_GENERAL + WHERE_ID + AND + EXISTING, id);
    }

    @Override
    public boolean archiveActionById(Boolean archive, Long questionId) throws DaoException {
        return updateEntityWithQuery(ARCHIVE_ACTIONS, archive, questionId);
    }

    @Override
    public boolean update(Question question) throws DaoException {
        return updateEntityWithQuery(UPDATE_QUESTION + WHERE_ID, question.getSectionId(), question.getTitle(),
                question.getText(), question.getId());
    }

    /**
     * Set mark for question
     *
     * @param questionId question id
     * @param userId     user, who set this mark
     * @param mark       mark to set
     * @return true - updated successfully<br>false - otherwise
     * @throws DaoException if some problems occurred inside
     */
    public boolean setQuestionMark(Long questionId, String userId, Integer mark) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NUM_OF_MARKS +
                WHERE_RELATION_ID)) {
            preparedStatement.setObject(1, questionId);
            preparedStatement.setObject(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean markExists = false;

            if (resultSet.next()) {
                markExists = resultSet.getInt(1) == MARK_EXISTS;
            }

            if (markExists) {
                return updateEntityWithQuery(UPDATE_QUESTION_MARK + WHERE_RELATION_ID, mark, questionId, userId);
            } else {
                return updateEntityWithQuery(INSERT_QUESTION_MARK, questionId, userId, mark);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in QuestionDao, setQuestionMark()", e);
        }
    }

    /**
     * Find question data
     *
     * @param questionId question id
     * @param isAdmin    true - admin<br>false - general user
     * @return QuestionData object
     * @throws DaoException if some problems occurred inside
     */
    public QuestionData findQuestionData(Long questionId, boolean isAdmin) throws DaoException {
        return findDataOnlyOne(SELECT_QUEST_USER_SECT + (isAdmin ? WHERE : WHERE + EXISTING + AND) + QUEST_ID, questionId);
    }

    /**
     * Find question data (with mark) (for authenticated users)
     *
     * @param questionId question id
     * @param isAdmin    true - admin<br>false - general user
     * @param login      current user login
     * @return QuestionData object
     * @throws DaoException if some problems occurred inside
     */
    public QuestionData findQuestionData(Long questionId, boolean isAdmin, String login) throws DaoException {
        if (login != null) {
            return findDataOnlyOne(SELECT_QUEST_USER_SECT_MARK + (isAdmin ? WHERE : WHERE + EXISTING + AND) + QUEST_ID, login, questionId);
        }

        return findQuestionData(questionId, isAdmin);
    }

    /**
     * Find questions from particular section (on particular page)
     *
     * @param sectionId section id
     * @param page      page to show
     * @param isAdmin   true - admin<br>false - general user
     * @return QuestionListWrapper object
     * @throws DaoException if some problems occurred inside
     */
    public QuestionListWrapper findQuestionsFromSection(Long sectionId, Integer page, boolean isAdmin)
            throws DaoException {
        return findWrapperDataBy(SELECT_QUEST_USER_SECT +
                (isAdmin ? WHERE : (WHERE + EXISTING + AND)) + FROM_PARTICULAR_SECTION + DESC_ORDER_BY_CREATE_DATE +
                OFFSET_AND_LIMIT, sectionId, pager.calculateListOffset(page), RECORDS_PER_PAGE);
    }

    /**
     * Find recent questions (on particular page)
     *
     * @param page page to show
     * @return QuestionListWrapper object
     * @throws DaoException if some problems occurred inside
     */
    public QuestionListWrapper findRecentQuestions(Integer page) throws DaoException {
        return findWrapperDataBy(SELECT_QUEST_USER_SECT + WHERE + EXISTING + DESC_ORDER_BY_CREATE_DATE + OFFSET_AND_LIMIT,
                pager.calculateListOffset(page), RECORDS_PER_PAGE);
    }

    /**
     * Find top questions with limit
     *
     * @param limit limit question number
     * @return question list
     * @throws DaoException if some problems occurred inside
     */
    public ArrayList<Question> findTopQuestions(Integer limit) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL +
                JOIN_ON_SECTION_GENERAL + WHERE + EXISTING + DESC_ORDER_BY_RATING + LIMIT)) {
            ArrayList<Question> questions = new ArrayList<>();
            preparedStatement.setInt(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questions.add(readEntity(resultSet));
            }

            return questions;
        } catch (SQLException e) {
            throw new DaoException("Exception in QuestionDao, findTopQuestions()", e);
        }
    }

    /**
     * Find top questions (on particular page)
     *
     * @param page page number to show
     * @return QuestionListWrapper object
     * @throws DaoException if some problems occurred inside
     */
    public QuestionListWrapper findTopQuestionsOnPage(Integer page) throws DaoException {
        return findWrapperDataBy(SELECT_QUEST_USER_SECT + WHERE + EXISTING + DESC_ORDER_BY_RATING + OFFSET_AND_LIMIT,
                pager.calculateListOffset(page), RECORDS_PER_PAGE);
    }

    private QuestionData findDataOnlyOne(String query, Object... params) throws DaoException {
        ArrayList<QuestionData> entities = findDataBy(query, params);

        if (!checkNull(entities) && !entities.isEmpty()) {
            return entities.get(0);
        }

        return null;
    }

    private QuestionListWrapper findWrapperDataBy(String query, Object... params) throws DaoException {
        QuestionListWrapper wrapper = new QuestionListWrapper();
        wrapper.setQuestionList(findDataBy(query, params));
        wrapper.setPagesNumber(pager.calculatePagesNumber(connection));
        return wrapper;
    }

    @Override
    protected ArrayList<QuestionData> extractData(ResultSet resultSet) throws DaoException {
        UserDao userDao = new UserDao(connection);
        SectionDao sectionDao = new SectionDao(connection);
        ArrayList<QuestionData> dataList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                QuestionData data = new QuestionData();
                data.setQuestion(readEntity(resultSet));
                data.setUser(userDao.readEntity(resultSet));
                data.setSection(sectionDao.readEntity(resultSet));
                data.setMark(resultSet.getInt("mark"));
                dataList.add(data);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in QuestionDao, extractData()", e);
        }

        return dataList;
    }

    @Override
    public boolean create(Question question) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, question.getAuthorId());
            preparedStatement.setLong(2, question.getSectionId());
            preparedStatement.setString(3, question.getTitle());
            preparedStatement.setString(4, question.getText());
            preparedStatement.setTimestamp(5, question.getCreationDate());
            preparedStatement.executeUpdate();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e) {
            throw new DaoException("Exception in QuestionDao, create()", e);
        }
    }

    @Override
    public Question readEntity(ResultSet resultSet) throws DaoException {
        try {
            Question question = new Question();
            question.setId(resultSet.getLong("question_id"));
            question.setAuthorId(resultSet.getString("author_id"));
            question.setSectionId(resultSet.getLong("section_id"));
            question.setTitle(resultSet.getString("title"));
            question.setText(resultSet.getString("text"));
            question.setCreationDate(resultSet.getTimestamp("creation_date"));
            question.setRating(resultSet.getFloat("rating"));
            question.setArchive(resultSet.getBoolean("archive"));
            return question;
        } catch (SQLException e) {
            throw new DaoException("Exception in QuestionDao, readEntity()", e);
        }
    }

    /**
     * Compose necessary question data: question itself, it's author, question's section and mark
     * which was set for it by current user
     */
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

    /**
     * Compose necessary questions list data: question list (QuestionData) and page number
     */
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
package com.kolyadko.likeit.dao.impl;

import com.kolyadko.likeit.dao.AbstractDao;
import com.kolyadko.likeit.entity.Question;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class QuestionDao extends AbstractDao<Integer, Question> {
    private static final String INSERT_COLUMNS = "author_id, section_id, title, text, creation_date";
    private static final String CREATE = "INSERT INTO question (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";
    private static final String WHERE_ID = " WHERE question_id=?";
    private static final String ARCHIVE_ACTIONS = "UPDATE question SET archive=?" + WHERE_ID;
    private static final String ALL_COLUMNS = "Q.question_id, Q.author_id, Q.section_id, Q.title, Q.text, Q.creation_date, " +
            "Q.comment_num, Q.answer_num, Q.mark_num, Q.rating, Q.archive";
    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM question Q";
    private static final String JOIN_ON_SECTION_GENERAL = " JOIN section S ON Q.section_id = S.section_id JOIN section MS " +
            "ON S.major_section_id = MS.section_id";
    private static final String EXISTING = " AND Q.archive=false AND S.archive=false AND MS.archive=false";
    private static final String UPDATE_COLUMNS = "section_id, title, text";
    private static final String UPDATE_USER = "UPDATE question SET " + String.join("=?, ", UPDATE_COLUMNS.split(",")) +
            "=?";
    //TODO stream API???

    public QuestionDao(ConnectionProxy connection) {
        super(connection);
    }

    public Question findById(Integer id) throws DaoException {
        return findOnlyOne(SELECT_ALL + WHERE_ID, id);
    }

    public Question findExistingById(Integer id) throws DaoException {
        return findOnlyOne(SELECT_ALL + JOIN_ON_SECTION_GENERAL + WHERE_ID + EXISTING, id);
    }

    public boolean archiveActionById(boolean archive, int questionId) throws DaoException {
        return updateEntityWithQuery(ARCHIVE_ACTIONS, archive, questionId);
    }

    public boolean updateQuestion(Question question) throws DaoException {
        return updateEntityWithQuery(UPDATE_USER + WHERE_ID, question.getSectionId(), question.getTitle(),
                question.getText(), question.getId());
    }

    @Override
    public boolean create(Question question) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, question.getAuthorId());
            preparedStatement.setInt(2, question.getSectionId());
            preparedStatement.setString(3, question.getTitle());
            preparedStatement.setString(4, question.getText());
            preparedStatement.setTimestamp(5, question.getCreationDate());
            preparedStatement.executeUpdate();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Question readEntity(ResultSet resultSet) throws DaoException {
        try {
            Question question = new Question();
            question.setId(resultSet.getInt("question_id"));
            question.setAuthorId(resultSet.getString("author_id"));
            question.setSectionId(resultSet.getInt("section_id"));
            question.setTitle(resultSet.getString("title"));
            question.setText(resultSet.getString("text"));
            question.setCreationDate(resultSet.getTimestamp("creation_date"));
            question.setCommentNum(resultSet.getInt("comment_num"));
            question.setAnswerNum(resultSet.getInt("answer_num"));
            question.setMarkNum(resultSet.getInt("mark_num"));
            question.setRating(resultSet.getFloat("rating"));
            question.setArchive(resultSet.getBoolean("archive"));
            return question;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
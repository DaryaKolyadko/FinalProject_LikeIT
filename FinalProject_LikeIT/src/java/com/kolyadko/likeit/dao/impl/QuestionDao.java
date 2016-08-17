package com.kolyadko.likeit.dao.impl;

import com.kolyadko.likeit.dao.AbstractDao;
import com.kolyadko.likeit.entity.Question;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class QuestionDao extends AbstractDao<Integer, Question> {
    private static final String ALL_COLUMNS = "question_id, author_id, section_id, title, text, creation_date, " +
            "version, last_modify, comment_num, answer_num, mark_num, rating, archive";
    private static final String INSERT_COLUMNS = "author_id, section_id, title, text";

    private static final String DESC_ORDER_BY_CREATE_DATE = " ORDER BY creation_date DESC";
//    private static final String DESC_ORDER_BY_RATING = " ORDER BY rating DESC";
//    private static final String EXISTING_QUEST = "  WHERE archive='false'";

    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM question";
    private static final String FIND_BY_ID = SELECT_ALL + "  WHERE question_id=?";
    private static final String CREATE = "INSERT INTO question (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";

    public QuestionDao(ConnectionWrapper connection) {
        super(connection);
    }

    @Override
    public Question findById(Integer id) throws DaoException {
        ArrayList<Question> questions = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questions.add(readEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            closeStatement(preparedStatement);
        }

        if (questions.size() > 0) {
            return questions.get(0);
        }

        return null;
    }

    @Override
    public ArrayList<Question> findAll() throws DaoException {
        return findWithStatement(SELECT_ALL + DESC_ORDER_BY_CREATE_DATE);
    }

    @Override
    public void create(Question question) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, question.getAuthorId());
            preparedStatement.setInt(2, question.getSectionId());
            preparedStatement.setString(3, question.getTitle());
            preparedStatement.setString(4, question.getText());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
        }
    }

//    public ArrayList<Question> findRecent() throws DaoException {
//        return findWithStatement(SELECT_ALL + EXISTING_QUEST + DESC_ORDER_BY_CREATE_DATE);
//    }
//
//    public ArrayList<Question> findTop() throws DaoException {
//        return findWithStatement(SELECT_ALL + EXISTING_QUEST + DESC_ORDER_BY_RATING);
//    }

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
            question.setVersion(resultSet.getByte("version"));
            question.setLastModify(resultSet.getTimestamp("last_modify"));
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
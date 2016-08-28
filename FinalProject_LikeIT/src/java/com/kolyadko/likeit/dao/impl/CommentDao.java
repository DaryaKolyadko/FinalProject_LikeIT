package com.kolyadko.likeit.dao.impl;

import com.kolyadko.likeit.dao.AbstractDao;
import com.kolyadko.likeit.entity.Comment;
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
public class CommentDao extends AbstractDao<Integer, Comment> {
    public CommentDao(ConnectionWrapper connection) {
        super(connection);
    }

    private static final String ALL_COLUMNS = "comment_id, author_id, question_id, text, creation_date, " +
            "version, last_modify, answer, mark_num, rating, archive";
    private static final String INSERT_COLUMNS = "author_id, question_id, text, creation_date";

    private static final String DESC_ORDER_BY_CREATE_DATE = " ORDER BY creation_date DESC";
    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM comment";
    private static final String FIND_BY_ID = SELECT_ALL + "  WHERE comment_id=?";
    private static final String CREATE = "INSERT INTO comment (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";

    @Override
    public Comment findById(Integer id) throws DaoException {
        return findOnlyOne(FIND_BY_ID, id);
    }

    @Override
    public ArrayList<Comment> findAll() throws DaoException {
        return findWithStatement(SELECT_ALL + DESC_ORDER_BY_CREATE_DATE);
    }

    @Override
    public void create(Comment comment) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, comment.getAuthorId());
            preparedStatement.setInt(2, comment.getQuestionId());
            preparedStatement.setString(3, comment.getText());
            preparedStatement.setTimestamp(4, comment.getCreationDate());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
        }
    }

    @Override
    public Comment readEntity(ResultSet resultSet) throws DaoException {
        try {
            Comment comment = new Comment();
            comment.setId(resultSet.getInt("comment_id"));
            comment.setAuthorId(resultSet.getString("author_id"));
            comment.setQuestionId(resultSet.getInt("question_id"));
            comment.setText(resultSet.getString("text"));
            comment.setCreationDate(resultSet.getTimestamp("creation_date"));
            comment.setVersion(resultSet.getByte("version"));
            comment.setLastModify(resultSet.getTimestamp("last_modify"));
            comment.setAnswer(resultSet.getBoolean("answer"));
            comment.setMarkNum(resultSet.getInt("mark_num"));
            comment.setRating(resultSet.getFloat("rating"));
            comment.setArchive(resultSet.getBoolean("archive"));
            return comment;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


}
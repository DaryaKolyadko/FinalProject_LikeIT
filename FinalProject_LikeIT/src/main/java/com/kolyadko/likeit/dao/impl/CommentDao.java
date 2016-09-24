package com.kolyadko.likeit.dao.impl;

import com.kolyadko.likeit.dao.AbstractDao;
import com.kolyadko.likeit.entity.Comment;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */

/**
 * This DAO allows perform operations on database with comments
 */
public class CommentDao extends AbstractDao<Integer, Comment> {
    public CommentDao(ConnectionProxy connection) {
        super(connection);
    }

    private static final String INSERT_COLUMNS = "author_id, question_id, text, creation_date";
    private static final String CREATE = "INSERT INTO comment (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";
    private static final String WHERE_ID = " WHERE comment_id=?";
    private static final String ARCHIVE_ACTIONS = "UPDATE comment SET archive=?" + WHERE_ID;
    private static final String UPDATE_COMMENT_ANSWER = "UPDATE comment SET answer=?";
    private static final String ALL_COLUMNS = "comment_id, author_id, question_id, text, creation_date, " +
            "answer, rating, archive";
    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM comment C";
    private static final String EXISTING = " AND C.archive=false";
    private static final String UPDATE_COMMENT = "UPDATE comment SET text=?";

    // comment data
    private static final String SELECT_GENERAL = "SELECT C.comment_id, author_id, question_id, text, creation_date, " +
            "answer, C.rating, C.archive, login, first_name, last_name, " +
            "gender, password, birth_date, sign_up_date, email, role, state, U.rating, U.archive ";
    private static final String SELECT_COMM_USER = SELECT_GENERAL + ", NULL as mark FROM comment C JOIN user U ON C.author_id = U.login";
    private static final String SELECT_COMM_USER_MARK = SELECT_GENERAL + ", ifnull(CR.mark,0) AS mark " +
            "FROM comment C JOIN user U ON C.author_id = U.login LEFT OUTER JOIN " +
            "(SELECT comment_id, mark FROM comment_rating WHERE user_id=?) CR ON C.comment_id=CR.comment_id";
    private static final String ORDER_BY_CREATE_DATE = " ORDER BY creation_date";
    private static final String WHERE_QUEST_ID = " WHERE question_id=?";

    // M-M relation (comment_rating table)
    private static final int MARK_EXISTS = 1;
    private static final String ALL_RELATION_COLUMNS = "comment_id, user_id, mark";
    private static final String WHERE_RELATION_ID = " WHERE comment_id=? AND user_id=?";
    private static final String SELECT_NUM_OF_MARKS = "SELECT COUNT(*) FROM comment_rating";
    private static final String UPDATE_COMMENT_MARK = "UPDATE comment_rating SET mark=?";
    private static final String INSERT_COMMENT_MARK = "INSERT INTO comment_rating (" + ALL_RELATION_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", ALL_RELATION_COLUMNS.split(",").length) + ");";

    /**
     * Move comment to archive\ restore comment from archive
     *
     * @param archive   true - move to<br>false - restore
     * @param commentId comment id
     * @return true - updated successfully<br>false - otherwise
     * @throws DaoException if some problems occurred inside
     */
    public boolean archiveActionById(boolean archive, int commentId) throws DaoException {
        return updateEntityWithQuery(ARCHIVE_ACTIONS, archive, commentId);
    }

    /**
     * Set mark for comment
     *
     * @param commentId comment id
     * @param userId    user, who set this mark
     * @param mark      mark to set
     * @return true - updated successfully<br>false - otherwise
     * @throws DaoException if some problems occurred inside
     */
    public boolean setCommentMark(int commentId, String userId, int mark) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NUM_OF_MARKS +
                WHERE_RELATION_ID)) {
            preparedStatement.setInt(1, commentId);
            preparedStatement.setString(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean markExists = false;

            if (resultSet.next()) {
                markExists = resultSet.getInt(1) == MARK_EXISTS;
            }

            if (markExists) {
                return updateEntityWithQuery(UPDATE_COMMENT_MARK + WHERE_RELATION_ID, mark, commentId, userId);
            } else {
                return updateEntityWithQuery(INSERT_COMMENT_MARK, commentId, userId, mark);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in CommentDao, setCommentMark()", e);
        }
    }

    /**
     * Note comment as answer or clean this note
     *
     * @param commentId comment id
     * @param state     true - answer<br>false - not an answer
     * @return true - updated successfully<br>false - otherwise
     * @throws DaoException if some problems occurred inside
     */
    public boolean noteAsAnswer(int commentId, boolean state) throws DaoException {
        return updateEntityWithQuery(UPDATE_COMMENT_ANSWER + WHERE_ID, state, commentId);
    }

    /**
     * Find Comment object by id
     *
     * @param id comment id
     * @return Comment object
     * @throws DaoException if some problems occurred inside
     */
    public Comment findById(Integer id) throws DaoException {
        return findOnlyOne(SELECT_ALL + WHERE_ID, id);
    }

    /**
     * Find Comment object which is not in archive by id
     *
     * @param id comment id
     * @return Comment object
     * @throws DaoException if some problems occurred inside
     */
    public Comment findExistingById(Integer id) throws DaoException {
        return findOnlyOne(SELECT_ALL + WHERE_ID + EXISTING, id);
    }

    /**
     * Update Comment object
     *
     * @param comment Comment object
     * @return true - updated successfully<br>false - otherwise
     * @throws DaoException if some problems occurred inside
     */
    @Override
    public boolean update(Comment comment) throws DaoException {
        return updateEntityWithQuery(UPDATE_COMMENT + WHERE_ID, comment.getText(), comment.getId());
    }

    /**
     * Find comments for particular question
     *
     * @param questionId question id
     * @param login      current user login
     * @param isAdmin    true - admin<br>false - general user
     * @return data of comment list
     * @throws DaoException if some problems occurred inside
     */
    public ArrayList<CommentData> findByQuestionId(Integer questionId, String login, boolean isAdmin) throws DaoException {
        if (login != null) {
            return findDataBy(SELECT_COMM_USER_MARK + WHERE_QUEST_ID + (isAdmin ? "" : EXISTING) + ORDER_BY_CREATE_DATE,
                    login, questionId);
        }

        return findDataBy(SELECT_COMM_USER + WHERE_QUEST_ID + EXISTING + ORDER_BY_CREATE_DATE, questionId);
    }

    @Override
    protected ArrayList<CommentData> extractData(ResultSet resultSet) throws DaoException {
        UserDao userDao = new UserDao(connection);
        ArrayList<CommentData> dataList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                CommentData data = new CommentData();
                data.setComment(readEntity(resultSet));
                data.setUser(userDao.readEntity(resultSet));
                data.setMark(resultSet.getInt("mark"));
                dataList.add(data);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception in CommentDao, extractData()", e);
        }

        return dataList;
    }

    /**
     * Compose necessary comment data: comment itself, it's author, mark which was set for it by
     * current user
     */
    public class CommentData {
        private Comment comment;
        private User user;
        private Integer mark;

        public Comment getComment() {
            return comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Integer getMark() {
            return mark;
        }

        public void setMark(Integer mark) {
            this.mark = mark;
        }
    }

    @Override
    public boolean create(Comment comment) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, comment.getAuthorId());
            preparedStatement.setInt(2, comment.getQuestionId());
            preparedStatement.setString(3, comment.getText());
            preparedStatement.setTimestamp(4, comment.getCreationDate());
            preparedStatement.executeUpdate();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e) {
            throw new DaoException("Exception in CommentDao, create()", e);
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
            comment.setAnswer(resultSet.getBoolean("answer"));
            comment.setRating(resultSet.getFloat("rating"));
            comment.setArchive(resultSet.getBoolean("archive"));
            return comment;
        } catch (SQLException e) {
            throw new DaoException("Exception in CommentDao, readEntity()", e);
        }
    }
}
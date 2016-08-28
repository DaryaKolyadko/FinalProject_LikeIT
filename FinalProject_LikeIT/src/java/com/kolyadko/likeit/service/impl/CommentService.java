package com.kolyadko.likeit.service.impl;

import com.kolyadko.likeit.dao.impl.CommentDao;
import com.kolyadko.likeit.dao.impl.UserDao;
import com.kolyadko.likeit.entity.Comment;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import com.kolyadko.likeit.service.AbstractService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class CommentService extends AbstractService<Integer, Comment> {
    private static final String SELECT_GENERAL = "SELECT C.comment_id, author_id, question_id, text, creation_date, " +
            "version, last_modify, answer, mark_num, C.rating, C.archive, login, avatar_id, first_name, last_name, " +
            "gender, password, birth_date, sign_up_date, email, email_confirmed, role, state, U.rating, U.answer_num, " +
            "question_num, comment_num, U.archive ";
    private static final String SELECT_COMM_USER = SELECT_GENERAL + ", NULL as mark FROM comment C JOIN user U ON C.author_id = U.login";
    private static final String SELECT_COMM_USER_MARK = SELECT_GENERAL + ", ifnull(CR.mark,0) AS mark " +
            "FROM comment C JOIN user U ON C.author_id = U.login LEFT OUTER JOIN " +
            "(SELECT comment_id, mark FROM comment_rating WHERE user_id=?) CR ON C.comment_id=CR.comment_id";
    private static final String ORDER_BY_CREATE_DATE = " ORDER BY creation_date";
    private static final String WHERE_QUEST_ID = " WHERE question_id=?";
    private static final String EXISTING = " AND C.archive=false";

    @Override
    public Comment findById(Integer id) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        CommentDao commentDao = new CommentDao(connection);

        try {
            return commentDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    @Override
    public void create(Comment comment) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        CommentDao commentDao = new CommentDao(connection);

        try {
            commentDao.create(comment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    public void findCommentsByAuthorId(String login) {
        // TODO
    }

    public ArrayList<CommentData> findByQuestionId(Integer questionId) throws ServiceException {
        return findBy(SELECT_COMM_USER + WHERE_QUEST_ID + EXISTING + ORDER_BY_CREATE_DATE, questionId);
    }

    public ArrayList<CommentData> findByQuestionId(Integer questionId, String login) throws ServiceException {
        if (login != null) {
            return findBy(SELECT_COMM_USER_MARK + WHERE_QUEST_ID + EXISTING + ORDER_BY_CREATE_DATE,
                    login, questionId);
        }

        return findByQuestionId(questionId);
    }

    private ArrayList<CommentData> findBy(String query, Object... params) throws ServiceException {
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

    private ArrayList<CommentData> extractData(ConnectionWrapper connection, ResultSet resultSet) throws ServiceException {
        CommentDao commentDao = new CommentDao(connection);
        UserDao userDao = new UserDao(connection);
        ArrayList<CommentData> dataList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                CommentData data = new CommentData();
                data.setComment(commentDao.readEntity(resultSet));
                data.setUser(userDao.readEntity(resultSet));
                data.setMark(resultSet.getInt("mark"));
                dataList.add(data);
            }
        } catch (DaoException | SQLException e) {
            throw new ServiceException(e);
        }

        return dataList;
    }

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
}
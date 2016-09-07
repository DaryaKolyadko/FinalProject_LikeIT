package com.kolyadko.likeit.dao.impl;

import com.kolyadko.likeit.dao.AbstractDao;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import com.kolyadko.likeit.type.GenderType;
import com.kolyadko.likeit.type.RoleType;
import com.kolyadko.likeit.type.StateType;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */
public class UserDao extends AbstractDao<String, User> {
    private static final String ALL_COLUMNS = "login, first_name, last_name, gender, password, birth_date, " +
            "sign_up_date, email, role, state, rating, answer_num, question_num, comment_num, archive";
    private static final String INSERT_COLUMNS = "login, first_name, last_name, gender, password, birth_date, " +
            "sign_up_date, email";
    //    private static final String UPDATE_COLUMNS = "first_name, last_name, gender, password, birth_date, email";
    private static final String UPDATE_COLUMNS = "first_name, last_name, gender, birth_date, email";

    private static final String ORDER_BY_LOGIN = " ORDER BY login";
    private static final String EXISTING = " archive='false'";// AND role='user'";
    private static final String AND = " AND";
    private static final String WHERE = " WHERE";

    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM user";
    private static final String FIND_BY_ID = SELECT_ALL + "  WHERE login=?";
    private static final String CREATE = "INSERT INTO user (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";
    private static final String WHERE_LOGIN = " WHERE login=?";
    private static final String ARCHIVE_ACTIONS = "UPDATE user SET archive=?" + WHERE_LOGIN;
    private static final String STATE_ACTIONS = "UPDATE user SET state=?" + WHERE_LOGIN;
    private static final String UPDATE_USER = "UPDATE user SET " + String.join("=?, ", UPDATE_COLUMNS.split(",")) +
            "=?";
    //TODO while stream api version crashes???
//            Arrays.stream(UPDATE_COLUMNS.split(",")).map((c) -> c + "=?").collect(Collectors.joining(", "));

    public UserDao(ConnectionProxy connection) {
        super(connection);
    }

    public User findById(String login) throws DaoException {
        return findOnlyOne(FIND_BY_ID, login);
    }

    public User findExistingById(String login) throws DaoException {
        return findOnlyOne(FIND_BY_ID + AND + EXISTING, login);
    }

    public ArrayList<User> findAll() throws DaoException {
        return findWithStatement(SELECT_ALL + ORDER_BY_LOGIN);
    }

    public ArrayList<User> findAllExisting() throws DaoException {
        return findWithStatement(SELECT_ALL + WHERE + EXISTING + ORDER_BY_LOGIN);
    }

    public boolean archiveActionById(boolean archive, String login) throws DaoException {
        return updateEntityWithQuery(ARCHIVE_ACTIONS, archive, login);
    }

    public boolean updateUser(User user) throws DaoException {
//        return updateEntityWithQuery(UPDATE_USER + WHERE_LOGIN, user.getFirstName(), user.getLastName(),
//                user.getGender().name(), user.getPassword(), user.getBirthDate(), user.getEmail());
        return updateEntityWithQuery(UPDATE_USER + WHERE_LOGIN, user.getFirstName(), user.getLastName(),
                user.getGender().name(), user.getBirthDate(), user.getEmail(), user.getId());
    }

    public boolean stateActionById(StateType state, String login) throws DaoException {
        return updateEntityWithQuery(STATE_ACTIONS, state.name(), login);
    }

    @Override
    public boolean create(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getGender().name());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setDate(6, user.getBirthDate());
            preparedStatement.setDate(7, user.getSignUpDate());
            preparedStatement.setString(8, user.getEmail());
            preparedStatement.executeUpdate();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public User readEntity(ResultSet resultSet) throws DaoException {
        try {
            User user = new User(resultSet.getString("login"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setGender(GenderType.getGenderType(resultSet.getString("gender")));
            user.setPassword(resultSet.getString("password"));
            user.setBirthDate(resultSet.getDate("birth_date"));
            user.setSignUpDate(resultSet.getDate("sign_up_date"));
            user.setEmail(resultSet.getString("email"));
            user.setRole(RoleType.getRoleType(resultSet.getString("role")));
            user.setState(StateType.getStateType(resultSet.getString("state")));
            user.setRating(resultSet.getFloat("rating"));
            user.setAnswerNum(resultSet.getInt("answer_num"));
            user.setQuestionNum(resultSet.getInt("question_num"));
            user.setCommentNum(resultSet.getInt("comment_num"));
            user.setArchive(resultSet.getBoolean("archive"));
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
package com.kolyadko.likeit.dao.impl;

import com.kolyadko.likeit.dao.AbstractDao;
import com.kolyadko.likeit.entity.Section;
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
public class SectionDao extends AbstractDao<Integer, Section> {
    private static final String ALL_COLUMNS = "section_id, major_section_id, name, question_num, answer_num, archive";
    private static final String INSERT_COLUMNS = "major_section_id, name";

    private static final String ORDER_BY_ID = " ORDER BY section_id";
    private static final String EXISTING = "  AND archive='false'";

    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM section";
    private static final String FIND_BY_ID = SELECT_ALL + "  WHERE section_id=?";
    private static final String FIND_BY_MAJOR_SECTION_ID = SELECT_ALL + "  WHERE major_section_id=?";
    private static final String FIND_MAJOR_SECTIONS = SELECT_ALL + "  WHERE major_section_id IS NULL";
    private static final String CREATE = "INSERT INTO section (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";

    public SectionDao(ConnectionWrapper connection) {
        super(connection);
    }

    @Override
    public Section findById(Integer id) throws DaoException {
        ArrayList<Section> sections = findBy(FIND_BY_ID, id);

        if (sections != null && sections.size() > 0) {
            return sections.get(0);
        }

        return null;
    }

    @Override
    public ArrayList<Section> findAll() throws DaoException {
        return findWithStatement(SELECT_ALL + ORDER_BY_ID);
    }

    @Override
    public void create(Section section) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setInt(1, section.getMajorSectionId());
            preparedStatement.setString(2, section.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
        }
    }

    public ArrayList<Section> findByMajorId(int majorSectionId) throws DaoException {
        return findBy(FIND_BY_MAJOR_SECTION_ID + ORDER_BY_ID, majorSectionId);
    }

    public ArrayList<Section> findExistingByMajorId(int majorSectionId) throws DaoException {
        return findBy(FIND_BY_MAJOR_SECTION_ID + EXISTING + ORDER_BY_ID, majorSectionId);
    }

    public ArrayList<Section> findMajorSections() throws DaoException {
        return findWithStatement(FIND_MAJOR_SECTIONS + ORDER_BY_ID);
    }

    public ArrayList<Section> findExistingMajorSections() throws DaoException {
        return findWithStatement(FIND_MAJOR_SECTIONS + EXISTING + ORDER_BY_ID);
    }

    private ArrayList<Section> findBy(String query, int someId) throws DaoException {
        ArrayList<Section> sections = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, someId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                sections.add(readEntity(resultSet));
            }

            return sections;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            closeStatement(preparedStatement);
        }
    }

    @Override
    protected Section readEntity(ResultSet resultSet) throws SQLException {
        Section section = new Section();
        section.setId(resultSet.getInt("section_id"));
        section.setMajorSectionId(resultSet.getInt("major_section_id"));
        section.setName(resultSet.getString("name"));
        section.setQuestionNum(resultSet.getInt("question_num"));
        section.setAnswerNum(resultSet.getInt("answer_num"));
        section.setArchive(resultSet.getBoolean("archive"));
        return section;
    }
}
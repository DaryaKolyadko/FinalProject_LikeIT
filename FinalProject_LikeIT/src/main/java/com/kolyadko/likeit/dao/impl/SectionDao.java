package com.kolyadko.likeit.dao.impl;

import com.kolyadko.likeit.dao.AbstractDao;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class SectionDao extends AbstractDao<Integer, Section> {
    private static final String ALL_COLUMNS = "S.section_id, S.major_section_id, S.name, S.question_num, S.answer_num, S.label_color, S.archive";
    private static final String INSERT_COLUMNS = "major_section_id, name, label_color";

    private static final String JOIN_ON_SECTION_GENERAL = " LEFT OUTER JOIN section MS ON S.major_section_id = MS.section_id";
    private static final String ORDER_BY_ID = " ORDER BY section_id";
    private static final String EXISTING = " AND S.archive='false' AND (MS.archive='false' OR MS.archive IS NULL)";

    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM section S";
    private static final String BY_ID = " WHERE S.section_id=?";
    private static final String BY_MAJOR_SECTION_ID = " WHERE S.major_section_id=?";
    private static final String MAJOR_SECTIONS = " WHERE S.major_section_id IS NULL";
    private static final String NOT_MAJOR_SECTIONS = "  WHERE S.major_section_id IS NOT NULL";
    private static final String CREATE = "INSERT INTO section (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";
    private static final String WHERE_ID = " WHERE section_id=?";
    private static final String ARCHIVE_ACTIONS = "UPDATE section SET archive=?" + WHERE_ID;
    private static final String UPDATE_NAME = "UPDATE section SET name=?";
    private static final String UPDATE_NAME_LABEL = UPDATE_NAME + ", label_color=?";

    public SectionDao(ConnectionProxy connection) {
        super(connection);
    }

    public Section findById(Integer id) throws DaoException {
        return findOnlyOne(SELECT_ALL + BY_ID, id);
    }

    public Section findExistingById(Integer id) throws DaoException {
        return findOnlyOne(SELECT_ALL + JOIN_ON_SECTION_GENERAL + BY_ID + EXISTING, id);
    }

    public ArrayList<Section> findByMajorId(int majorSectionId) throws DaoException {
        return findBy(SELECT_ALL + BY_MAJOR_SECTION_ID + ORDER_BY_ID, majorSectionId);
    }

    public ArrayList<Section> findExistingByMajorId(int majorSectionId) throws DaoException {
        return findBy(SELECT_ALL + JOIN_ON_SECTION_GENERAL + BY_MAJOR_SECTION_ID + EXISTING + ORDER_BY_ID, majorSectionId);
    }

    public ArrayList<Section> findMajorSections() throws DaoException {
        return findWithStatement(SELECT_ALL + MAJOR_SECTIONS + ORDER_BY_ID);
    }

    public ArrayList<Section> findExistingMajorSections() throws DaoException {
        return findWithStatement(SELECT_ALL + JOIN_ON_SECTION_GENERAL + MAJOR_SECTIONS + EXISTING + ORDER_BY_ID);
    }

    public ArrayList<Section> findExistingNotMajorSections() throws DaoException {
        return findWithStatement(SELECT_ALL + JOIN_ON_SECTION_GENERAL + NOT_MAJOR_SECTIONS + EXISTING);
    }

    public boolean archiveActionById(boolean archive, int sectionId) throws DaoException {
        return updateEntityWithQuery(ARCHIVE_ACTIONS, archive, sectionId);
    }

    public boolean updateNotMajorSection(Section section) throws DaoException {
        return updateEntityWithQuery(UPDATE_NAME + WHERE_ID, section.getName(), section.getId());
    }

    public boolean updateMajorSection(Section section) throws DaoException {
        return updateEntityWithQuery(UPDATE_NAME_LABEL + WHERE_ID, section.getName(), section.getLabelColor(),
                section.getId());
    }

    @Override
    public boolean create(Section section) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {

            if (section.getMajorSectionId() != null) {
                preparedStatement.setInt(1, section.getMajorSectionId());
            } else {
                preparedStatement.setNull(1, Types.INTEGER);
            }

            preparedStatement.setString(2, section.getName());
            preparedStatement.setString(3, section.getLabelColor());
            preparedStatement.executeUpdate();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e) {
            throw new DaoException("Exception in SectionDao, create()", e);
        }
    }

    @Override
    public Section readEntity(ResultSet resultSet) throws DaoException {
        try {
            Section section = new Section();
            section.setId(resultSet.getInt("section_id"));
            section.setMajorSectionId(resultSet.getInt("major_section_id"));
            section.setName(resultSet.getString("name"));
            section.setQuestionNum(resultSet.getInt("question_num"));
            section.setAnswerNum(resultSet.getInt("answer_num"));
            section.setLabelColor(resultSet.getString("label_color"));
            section.setArchive(resultSet.getBoolean("archive"));
            return section;
        } catch (SQLException e) {
            throw new DaoException("Exception in SectionDao, readEntity()", e);
        }
    }
}
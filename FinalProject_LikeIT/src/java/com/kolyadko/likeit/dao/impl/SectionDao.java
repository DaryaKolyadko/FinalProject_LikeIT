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
    private static final String ALL_COLUMNS = "section_id, major_section_id, name, question_num, answer_num, label_color, archive";
    private static final String INSERT_COLUMNS = "major_section_id, name, label_color";

    private static final String ORDER_BY_ID = " ORDER BY section_id";
    private static final String EXISTING = "  AND archive='false'";

    private static final String SELECT_ALL = "SELECT " + ALL_COLUMNS + " FROM section";
    //    private static final String SECTION_ID_IN = "  WHERE section_id IN";
//    private static final String SELECT_IN = SELECT_ALL + SECTION_ID_IN;
    private static final String FIND_BY_ID = SELECT_ALL + "  WHERE section_id=?";
    private static final String FIND_BY_NAME = SELECT_ALL + "  WHERE name=?";
    private static final String FIND_BY_MAJOR_SECTION_ID = SELECT_ALL + "  WHERE major_section_id=?";
    private static final String FIND_MAJOR_SECTIONS = SELECT_ALL + "  WHERE major_section_id IS NULL";
    private static final String CREATE = "INSERT INTO section (" + INSERT_COLUMNS + ") VALUES(" +
            StringUtils.repeat("?", ", ", INSERT_COLUMNS.split(",").length) + ");";

    public SectionDao(ConnectionWrapper connection) {
        super(connection);
    }

    @Override
    public Section findById(Integer id) throws DaoException {
        return findOnlyOne(FIND_BY_ID, id);
    }

    public Section findByName(String name) throws DaoException {
        return findOnlyOne(FIND_BY_NAME, name);
    }

    public Section findExistingByName(String name) throws DaoException {
        return findOnlyOne(FIND_BY_NAME + EXISTING, name);
    }

    private Section findOnlyOne(String query, Object param) throws DaoException {
        ArrayList<Section> sections = findBy(query, param);

        if (sections != null && !sections.isEmpty()) {
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
            preparedStatement.setString(3, section.getLabelColor());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
        }
    }

    public ArrayList<Section> findByMajorId(Integer majorSectionId) throws DaoException {
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

    private ArrayList<Section> findBy(String query, Object param) throws DaoException {
        ArrayList<Section> sections = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, param);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                sections.add(readEntity(resultSet));
            }

            return sections;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(preparedStatement);
        }
    }

//    public HashMap<Integer, Section> findByIdIn(Integer[] indexes) throws DaoException {
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet;
//        HashMap<Integer, Section> sections = new HashMap<>();
//
//        try {
//            preparedStatement = connection.prepareStatement(SELECT_IN + "(" +
//                    StringUtils.repeat("?", ", ", indexes.length) + ");");
//
//            for (int i = 0; i < indexes.length; i++) {
//                preparedStatement.setInt(i + 1, indexes[i]);
//            }
//
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                Section section = readEntity(resultSet);
//                sections.put(section.getId(), section);
//            }
//
//            return sections;
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            closeStatement(preparedStatement);
//        }
//    }
//
//    public HashMap<Integer, Section> findMajorSectionsOf(Integer[] indexes) throws DaoException {
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet;
//        HashMap<Integer, Section> sections = new HashMap<>();
//
//        try {
//            String indexesStr = StringUtils.repeat("?", ", ", indexes.length);
//            preparedStatement = connection.prepareStatement(SELECT_ALL + SECTION_ID_IN + "(" +
//                    indexesStr + ") AND major_section_id IS NULL UNION " + SELECT_IN + " (SELECT major_section_id FROM section " +
//                    SECTION_ID_IN + " (" + indexesStr + ") AND major_section_id IS NOT NULL);");
//
//            int len = indexes.length * 2;
//
//            for (int i = 0; i < len; i++) {
//                preparedStatement.setInt(i + 1, indexes[i % indexes.length]);
//            }
//
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                Section s = readEntity(resultSet);
//                sections.put(s.getId(), s);
//            }
//
//            return sections;
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            closeStatement(preparedStatement);
//        }
//    }

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
            throw new DaoException(e);
        }
    }
}
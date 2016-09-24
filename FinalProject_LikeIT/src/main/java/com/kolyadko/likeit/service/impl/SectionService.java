package com.kolyadko.likeit.service.impl;

import com.kolyadko.likeit.dao.impl.SectionDao;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionProxy;
import com.kolyadko.likeit.service.AbstractService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */

/**
 * This Service allows perform operations on database with sections
 */
public class SectionService extends AbstractService<Integer, Section> {
    @Override
    public Section findById(Integer sectionId, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            SectionDao sectionDao = new SectionDao(connection);
            return isAdmin ? sectionDao.findById(sectionId) : sectionDao.findExistingById(sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in SectionService, findById()", e);
        }
    }

    @Override
    public boolean create(Section section) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            SectionDao sectionDao = new SectionDao(connection);
            prepare(section);
            return sectionDao.create(section);
        } catch (DaoException e) {
            throw new ServiceException("Exception in SectionService, create()", e);
        }
    }

    @Override
    public boolean update(Section section) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            SectionDao sectionDao = new SectionDao(connection);
            prepare(section);
            return sectionDao.update(section);
        } catch (DaoException e) {
            throw new ServiceException("Exception in SectionService, updateSection()", e);
        }
    }

    /**
     * Find all not major sections
     *
     * @return Section list
     * @throws ServiceException if some problems occurred inside
     */
    public ArrayList<Section> findNotMajorSections() throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            SectionDao sectionDao = new SectionDao(connection);
            return sectionDao.findExistingNotMajorSections();
        } catch (DaoException e) {
            throw new ServiceException("Exception in SectionService, findNotMajorSections()", e);
        }
    }

    /**
     * Fid all major sections
     *
     * @return Section list
     * @throws ServiceException if some problems occurred inside
     */
    public ArrayList<Section> findMajorSections() throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            SectionDao sectionDao = new SectionDao(connection);
            return sectionDao.findExistingMajorSections();
        } catch (DaoException e) {
            throw new ServiceException("Exception in SectionService, findMajorSections()", e);
        }
    }

    /**
     * Select sections catalogue tree
     *
     * @param isAdmin true - admin<br>false - general user
     * @return HashMap: major sections and their subsections
     * @throws ServiceException if some problems occurred inside
     */
    public HashMap selectSectionsCatalogueTree(boolean isAdmin) throws ServiceException {
        LinkedHashMap<Section, ArrayList<Section>> catalogue = new LinkedHashMap<>();
        ArrayList<Section> majorSections;

        try (ConnectionProxy connection = getConnectionProxy()) {
            SectionDao sectionDao = new SectionDao(connection);
            majorSections = isAdmin ? sectionDao.findMajorSections() : sectionDao.findExistingMajorSections();

            for (Section majorSection : majorSections) {
                ArrayList<Section> sections = isAdmin ? sectionDao.findByMajorId(majorSection.getId()) :
                        sectionDao.findExistingByMajorId(majorSection.getId());
                catalogue.put(majorSection, sections);
            }

            return catalogue;
        } catch (DaoException e) {
            throw new ServiceException("Exception in SectionService, selectSectionsCatalogueTree()", e);
        }
    }

    @Override
    public boolean moveToArchive(Integer sectionId) throws ServiceException {
        return archiveActionsById(true, sectionId);
    }

    @Override
    public boolean restoreFromArchive(Integer sectionId) throws ServiceException {
        return archiveActionsById(false, sectionId);
    }

    private boolean archiveActionsById(boolean archive, int sectionId) throws ServiceException {
        try (ConnectionProxy connection = getConnectionProxy()) {
            SectionDao sectionDao = new SectionDao(connection);
            return sectionDao.archiveActionById(archive, sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in SectionService, archiveActionsById()", e);
        }
    }

    private void prepare(Section section) {
        if (section.getLabelColor() != null) { //  color in hex #... => remove '#'
            section.setLabelColor(StringUtils.substringAfter(section.getLabelColor(), "#"));
        }
    }
}
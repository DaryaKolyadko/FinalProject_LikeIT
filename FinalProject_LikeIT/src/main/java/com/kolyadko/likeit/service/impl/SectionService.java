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
public class SectionService extends AbstractService<Integer, Section> {
    public Section findById(Integer sectionId, boolean isAdmin) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            SectionDao sectionDao = new SectionDao(connection);
            return isAdmin ? sectionDao.findById(sectionId) : sectionDao.findExistingById(sectionId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean create(Section section) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            SectionDao sectionDao = new SectionDao(connection);
            prepare(section);
            return sectionDao.create(section);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean updateSection(Section section) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            SectionDao sectionDao = new SectionDao(connection);
            prepare(section);

            if (section.isMajor()) {
                return sectionDao.updateMajorSection(section);
            } else {
                return sectionDao.updateNotMajorSection(section);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<Section> findNotMajorSections() throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            SectionDao sectionDao = new SectionDao(connection);
            return sectionDao.findExistingNotMajorSections();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<Section> findMajorSections() throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            SectionDao sectionDao = new SectionDao(connection);
            return sectionDao.findExistingMajorSections();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public HashMap selectSectionsCatalogueTree(boolean isAdmin) throws ServiceException {
        LinkedHashMap<Section, ArrayList<Section>> catalogue = new LinkedHashMap<>();
        ArrayList<Section> majorSections;

        try (ConnectionProxy connection = getConnectionWrapper()) {
            SectionDao sectionDao = new SectionDao(connection);
            majorSections = isAdmin ? sectionDao.findMajorSections() : sectionDao.findExistingMajorSections();

            for (Section majorSection : majorSections) {
                ArrayList<Section> sections = isAdmin ? sectionDao.findByMajorId(majorSection.getId()) :
                        sectionDao.findExistingByMajorId(majorSection.getId());
                catalogue.put(majorSection, sections);
            }

            return catalogue;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean moveSectionToArchive(int sectionId) throws ServiceException {
        return archiveActionsById(true, sectionId);
    }

    public boolean restoreSectionFromArchive(int sectionId) throws ServiceException {
        return archiveActionsById(false, sectionId);
    }

    private boolean archiveActionsById(boolean archive, int sectionId) throws ServiceException {
        try (ConnectionProxy connection = getConnectionWrapper()) {
            SectionDao sectionDao = new SectionDao(connection);
            return sectionDao.archiveActionById(archive, sectionId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void prepare(Section section) {
        if (section.getLabelColor() != null) { //  color in hex #... => remove '#'
            section.setLabelColor(StringUtils.substringAfter(section.getLabelColor(), "#"));
        }
    }
}
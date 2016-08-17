package com.kolyadko.likeit.service.impl;

import com.kolyadko.likeit.dao.impl.SectionDao;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.exception.DaoException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import com.kolyadko.likeit.service.AbstractService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class SectionService extends AbstractService<Integer, Section> {
    @Override
    public Section findById(Integer id) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        SectionDao sectionDao = new SectionDao(connection);

        try {
            return sectionDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    @Override
    public void create(Section section) throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        SectionDao sectionDao = new SectionDao(connection);

        try {
            sectionDao.create(section);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

    public HashMap selectSectionsCatalogueTree(boolean isAdmin)
            throws ServiceException {
        ConnectionWrapper connection = getConnectionWrapper();
        SectionDao sectionDao = new SectionDao(connection);
        LinkedHashMap<Section, ArrayList<Section>> catalogue = new LinkedHashMap<>();
        ArrayList<Section> majorSections;

        try {
            majorSections = isAdmin ? sectionDao.findMajorSections() : sectionDao.findExistingMajorSections();

            for (Section majorSection : majorSections) {
                ArrayList<Section> sections = isAdmin ? sectionDao.findByMajorId(majorSection.getId()) :
                        sectionDao.findExistingByMajorId(majorSection.getId());

                catalogue.put(majorSection, sections);
            }

            return catalogue;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            connection.close();
        }
    }

//    public HashMap<Integer, Section> findByIdIn(Integer[] indexes) throws ServiceException {
//        ConnectionWrapper connection = getConnectionWrapper();
//        SectionDao sectionDao = new SectionDao(connection);
//
//        try {
//            return sectionDao.findByIdIn(indexes);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        } finally {
//            connection.close();
//        }
//    }
//
//    public HashMap<Integer, Section> findMajorSectionsOf(Integer[] indexes) throws ServiceException {
//        ConnectionWrapper connection = getConnectionWrapper();
//        SectionDao sectionDao = new SectionDao(connection);
//
//        try {
//            return sectionDao.findMajorSectionsOf(indexes);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        } finally {
//            connection.close();
//        }
//    }
}
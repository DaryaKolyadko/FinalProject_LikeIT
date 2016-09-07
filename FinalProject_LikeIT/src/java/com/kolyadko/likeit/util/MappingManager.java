package com.kolyadko.likeit.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by DaryaKolyadko on 26.07.2016.
 */
public class MappingManager {
    private static final Logger LOG = LogManager.getLogger(MappingManager.class);
    private static final String ERROR = "Application cannot run without config file: ";
    public static final String HOME_PAGE = "/LikeIT/Home";
    public static final String LOGIN_PAGE = "/LikeIT/Login";
    public static final String SIGN_UP_PAGE = "/LikeIT/SignUp";
    public static final String ABOUT_PAGE = "/LikeIT/About";
    public static final String TOP_PAGE = "/LikeIT/TOP";
    public static final String RECENT_PAGE = "/LikeIT/Recent";
    public static final String QUESTIONS_PAGE = "/LikeIT/Questions";
    public static final String QUESTION_PAGE = "/LikeIT/Question";
    public static final String CREATE_QUESTION_PAGE = "/LikeIT/CreateQuestion";
    public static final String CREATE_SECTION_PAGE = "/LikeIT/CreateSection";
    public static final String EDIT_SECTION_PAGE = "/LikeIT/EditSection";
    public static final String EDIT_QUESTION_PAGE = "/LikeIT/EditQuestion";
    public static final String EDIT_PROFILE_PAGE = "/LikeIT/EditProfile";
    public static final String USER_LIST_PAGE = "/LikeIT/Users";
    public static final String PROFILE_PAGE = "/LikeIT/Profile";
    public static final String ERROR_PAGE_404 = "/LikeIT/Error404";
    public static final String ERROR_PAGE_500 = "/LikeIT/Error500";

    private static String configFileName = "mapping.properties";
    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static Lock managerSingleLock = new ReentrantLock();

    private static Properties configuration;
    private static MappingManager manager;

    private MappingManager() {
        try {
            configuration = new Properties();
            configuration.load(MappingManager.class.getClassLoader().getResourceAsStream(configFileName));
        } catch (IOException e) {
            LOG.error(ERROR);
            throw new RuntimeException(ERROR + configFileName);
        }
    }

//    public static boolean changeConfigFile(String newConfigFile) {
//        managerSingleLock.lock();
//        boolean result = false;
//
//        if (manager == null) {
//            configFileName = newConfigFile;
//            result = true;
//        }
//
//        managerSingleLock.unlock();
//        return result;
//    }

    public static MappingManager getInstance() {
        if (!initialized.get()) {
            managerSingleLock.lock();

            if (manager == null) {
                manager = new MappingManager();
                initialized.set(true);
            }

            managerSingleLock.unlock();
        }

        return manager;
    }

    public String getProperty(String key) {
        return configuration.getProperty(key);
    }
}
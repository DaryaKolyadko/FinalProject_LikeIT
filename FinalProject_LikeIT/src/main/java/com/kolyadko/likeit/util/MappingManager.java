package com.kolyadko.likeit.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by DaryaKolyadko on 26.07.2016.
 */

/**
 * Beautiful URI --- real jsp path mapping manager
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
    public static final String EDIT_COMMENT_PAGE = "/LikeIT/EditComment";
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
        configuration = new Properties();
        URL configFile = MappingManager.class.getClassLoader().getResource(configFileName);

        if (configFile == null) {
            throw new RuntimeException(ERROR + configFileName);
        }

        try (FileInputStream inputStream = new FileInputStream(configFile.getFile())) {
            configuration.load(inputStream);
        } catch (IOException e) {
            LOG.error(ERROR, e);
            throw new RuntimeException(ERROR + configFileName);
        }
    }

    /**
     * Singleton method
     *
     * @return class instance
     */
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

    /**
     * Return property value
     * @param key key
     * @return property value
     */
    public String getProperty(String key) {
        return configuration.getProperty(key);
    }

    /**
     * Get config file name
     *
     * @return config file name
     */
    public static String getConfigFileName() {
        return configFileName;
    }

    /**
     * Set new config file name
     *
     * @param configFile config file name
     */
    public static void setConfigFileName(String configFile) {
        MappingManager.configFileName = configFile;
    }
}
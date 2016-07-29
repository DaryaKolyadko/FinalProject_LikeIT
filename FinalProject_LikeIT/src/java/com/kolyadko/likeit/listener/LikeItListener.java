package com.kolyadko.likeit.listener;

import com.kolyadko.likeit.pool.ConnectionPool;
import com.kolyadko.likeit.pool.ConnectionWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by DaryaKolyadko on 16.07.2016.
 */
public class LikeItListener implements ServletContextListener {
    private static final Logger LOG = LogManager.getLogger(ConnectionWrapper.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //TODO
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //TODO
        ConnectionPool.getInstance().closeAll();
    }
}
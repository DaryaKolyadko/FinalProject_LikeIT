package com.kolyadko.likeit.pool;


import com.kolyadko.likeit.exception.ConnectionPoolException;
import com.kolyadko.likeit.exception.DatabaseConnectorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */
public class ConnectionPool {
    private static final int POOL_SIZE = 2;
    private static final int TIMEOUT_NOT_APPLIED = 0;
    private static final Logger LOG = LogManager.getLogger(ConnectionPool.class);

    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static Lock poolSingleLock = new ReentrantLock();
    private BlockingQueue<ConnectionProxy> connectionsAvailable;
    private BlockingQueue<ConnectionProxy> connectionsInUse;
    private static ConnectionPool pool;

    private ConnectionPool() {
        connectionsAvailable = new ArrayBlockingQueue<>(POOL_SIZE);
        connectionsInUse = new ArrayBlockingQueue<>(POOL_SIZE);

        do {
            init();

            if (connectionsAvailable.isEmpty()) {
                LOG.fatal("Pool wasn't initialized");
                throw new RuntimeException("Pool wasn't initialized");
            }
        }
        while (connectionsAvailable.size() != POOL_SIZE);
    }

    private void init() {
        int needConnectionsNumber = POOL_SIZE - connectionsAvailable.size();

        for (int i = 0; i < needConnectionsNumber; i++) {
            try {
                ConnectionProxy connectionProxy = new ConnectionProxy(DatabaseConnector.getConnection());
                connectionProxy.setAutoCommit(true);
                connectionsAvailable.put(connectionProxy);
            } catch (DatabaseConnectorException | InterruptedException | SQLException e) {
                LOG.error(e);
            }
        }
    }

    public static boolean isInitialized() {
        return initialized.get();
    }

    public static ConnectionPool getInstance() {
        if (!initialized.get()) {
            poolSingleLock.lock();

            try {
                if (pool == null) {
                    pool = new ConnectionPool();
                    initialized.set(true);
                }
            } finally {
                poolSingleLock.unlock();
            }
        }

        return pool;
    }

    public ConnectionProxy getConnection() throws ConnectionPoolException {
        ConnectionProxy connectionProxy;

        try {
            connectionProxy = connectionsAvailable.take();
            connectionsInUse.put(connectionProxy);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        }

        return connectionProxy;
    }

    public void closeConnection(ConnectionProxy connectionProxy) throws ConnectionPoolException {
        boolean success = connectionsInUse.remove(connectionProxy);

        try {
            if (success && connectionProxy.isValid(TIMEOUT_NOT_APPLIED)) {
                connectionsAvailable.put(connectionProxy);
            } else {
                ConnectionProxy newConnection = new ConnectionProxy(DatabaseConnector.getConnection());
                newConnection.setAutoCommit(true);
                connectionsAvailable.put(newConnection);
            }
        } catch (SQLException | DatabaseConnectorException | InterruptedException e) {
            throw new ConnectionPoolException(e);
        }
    }

    public void closeAll() {
        if (initialized.get()) {
            initialized.set(false);

            for (ConnectionProxy connectionProxy : connectionsAvailable) {
                try {
                    if (!connectionProxy.getAutoCommit()) {
                        connectionProxy.setAutoCommit(true);
                    }

                    connectionProxy.finallyClose();
                    connectionsAvailable.remove(connectionProxy);
                    LOG.info("closed successfully (available)");
                } catch (SQLException e) {
                    LOG.warn("problem with connection closing (available)");
                }
            }

            for (ConnectionProxy connectionProxy : connectionsInUse) {
                try {
                    connectionProxy.finallyClose();
                    connectionsInUse.remove(connectionProxy);
                    LOG.info("close successfully (inUse)");
                } catch (SQLException e) {
                    LOG.warn("problem with connection closing (inUse)");
                }
            }
        }
    }
}
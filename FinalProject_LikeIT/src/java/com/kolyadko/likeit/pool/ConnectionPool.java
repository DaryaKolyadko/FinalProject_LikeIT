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
    private BlockingQueue<ConnectionWrapper> connectionsAvailable;
    private BlockingQueue<ConnectionWrapper> connectionsInUse;
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
                ConnectionWrapper connectionWrapper = new ConnectionWrapper(DatabaseConnector.getConnection());
                connectionWrapper.setAutoCommit(true);
                connectionsAvailable.put(connectionWrapper);
            } catch (DatabaseConnectorException | InterruptedException | SQLException e) {
                LOG.error(e.getMessage());
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

    public ConnectionWrapper getConnection() throws ConnectionPoolException {
        ConnectionWrapper connectionWrapper;

        try {
            connectionWrapper = connectionsAvailable.take();
            connectionsInUse.put(connectionWrapper);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e.getMessage());
        }

        return connectionWrapper;
    }

    public void closeConnection(ConnectionWrapper connectionWrapper) throws ConnectionPoolException {
        boolean success = connectionsInUse.remove(connectionWrapper);

        try {
            if (success && connectionWrapper.isValid(TIMEOUT_NOT_APPLIED)) {
                connectionsAvailable.put(connectionWrapper);
            } else {
                ConnectionWrapper newConnection = new ConnectionWrapper(DatabaseConnector.getConnection());
                newConnection.setAutoCommit(true);
                connectionsAvailable.put(newConnection);
            }
        } catch (SQLException | DatabaseConnectorException | InterruptedException e) {
            throw new ConnectionPoolException(e.getMessage());
        }
    }

    public void closeAll() {
        if (initialized.get()) {
            initialized.set(false);

            for (ConnectionWrapper connectionWrapper : connectionsAvailable) {
                try {
                    if (!connectionWrapper.getAutoCommit()) {
                        connectionWrapper.setAutoCommit(true);
                    }

                    connectionWrapper.finallyClose();
                    connectionsAvailable.remove(connectionWrapper);
                    LOG.info("closed successfully (available)");
                } catch (SQLException e) {
                    LOG.warn("problem with connection closing (available)");
                }
            }

            for (ConnectionWrapper connectionWrapper : connectionsInUse) {
                try {
                    connectionWrapper.finallyClose();
                    connectionsInUse.remove(connectionWrapper);
                    LOG.info("close successfully (inUse)");
                } catch (SQLException e) {
                    LOG.warn("problem with connection closing (inUse)");
                }
            }
        }
    }
}
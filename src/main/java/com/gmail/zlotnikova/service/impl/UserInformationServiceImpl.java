package com.gmail.zlotnikova.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.zlotnikova.repository.ConnectionRepository;
import com.gmail.zlotnikova.repository.UserInformationRepository;
import com.gmail.zlotnikova.repository.impl.ConnectionRepositoryImpl;
import com.gmail.zlotnikova.repository.impl.UserInformationRepositoryImpl;
import com.gmail.zlotnikova.service.UserInformationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserInformationServiceImpl implements UserInformationService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static UserInformationService instance;
    private ConnectionRepository connectionRepository;
    private UserInformationRepository userInformationRepository;

    private UserInformationServiceImpl(
            ConnectionRepository connectionRepository, UserInformationRepository userInformationRepository) {
        this.connectionRepository = connectionRepository;
        this.userInformationRepository = userInformationRepository;
    }

    public static UserInformationService getInstance() {
        if (instance == null) {
            instance = new UserInformationServiceImpl(
                    ConnectionRepositoryImpl.getInstance(), UserInformationRepositoryImpl.getInstance());
        }
        return instance;
    }

    @Override
    public int updateAddressById(int id, String newAddress) {
        int noRowsUpdated = 0;
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int rowsUpdated = userInformationRepository.updateAddressById(connection, id, newAddress);
                connection.commit();
                return rowsUpdated;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return noRowsUpdated;
    }

}
package com.gmail.zlotnikova.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.zlotnikova.repository.model.UserInformation;

public interface UserInformationRepository extends GeneralRepository<UserInformation> {

    int updateAddressById(Connection connection, int id, String newAddress) throws SQLException;

}
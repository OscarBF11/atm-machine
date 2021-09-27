package com.database;

import com.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper{

    @Override
    public User mapRow(ResultSet resultSet, int row) throws SQLException {
        int userAccountNumber = resultSet.getInt("accountNumber");
        String userPin = resultSet.getString("pin");
        int openingBalance = resultSet.getInt("openingBalance");
        int overdraft = resultSet.getInt("overdraft");

        User user = new User(userAccountNumber, userPin, openingBalance, overdraft);

        return user;
    }
}

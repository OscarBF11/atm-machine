package com.database;

import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BankRowMapper bankRowMapper;

    public List<User> findAll(){
        return jdbcTemplate.query("select * from bank", bankRowMapper);
    }

    public User findByAccountNumber(int accountNumber){
        return jdbcTemplate.queryForObject("select * from user where accountNumber=?",
                new Object[] { accountNumber },
                new BeanPropertyRowMapper<>(User.class));
    }

    public int updateOverdraft(int accountNumber, int overdraft) {
        return jdbcTemplate.update("update user set overdraft = ? where accountNumber = ?",
                overdraft, accountNumber);
    }
}

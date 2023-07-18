package com.shopping.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

public class UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    public User getDetails(String username) {
        User user = new User();
        try {
            Connection connection = H2DatabaseConnection.getConnectionToDatabase();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setGender(resultSet.getString("gender"));
            }
        }
        catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return user;
    }
}

package com.shopping.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDao {
    private Logger logger = Logger.getLogger(OrderDao.class.getName());

    public List<Order> getOrders(int userId) {
        Connection connection =null;
        List<Order> orders = new ArrayList<>();
        try {
        connection= H2DatabaseConnection.getConnectionToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM orders WHERE user_id = ?");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setUserId(resultSet.getInt("user_id"));
                order.setOrderId(resultSet.getInt("order_id"));
                order.setNoOfItems(resultSet.getInt("no_of_items"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));
                order.setOrderDate(resultSet.getDate("order_date"));
                orders.add(order);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Could Not execute query",e.getMessage());
        }
return orders;
    }

}

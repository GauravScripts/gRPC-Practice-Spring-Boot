package com.shopping.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int userId;
    private int orderId;
    private int noOfItems;
    private double totalAmount;
    private Date orderDate;
}

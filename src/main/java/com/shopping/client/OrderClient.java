package com.shopping.client;

import com.shopping.stubs.order.Order;
import com.shopping.stubs.order.OrderRequest;
import com.shopping.stubs.order.OrderResponse;
import com.shopping.stubs.order.OrderServiceGrpc;
import io.grpc.Channel;

import java.util.List;
import java.util.logging.Logger;

public class OrderClient {
    private Logger logger =  Logger.getLogger(OrderClient.class.getName());
    // get a stub object for the remote object
    // call the service method
    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

    public OrderClient(Channel channel) {
       orderServiceBlockingStub = OrderServiceGrpc.newBlockingStub(channel);
    }
    public List<Order> getOrders(int userId) {
        logger.info("Order Client Calling the Order Service");
        OrderRequest orderRequest = OrderRequest.newBuilder().setUserId(userId).build();
        OrderResponse orderResponse = orderServiceBlockingStub.getOrdersForUser(orderRequest);
        return orderResponse.getOrderList();
    }



}

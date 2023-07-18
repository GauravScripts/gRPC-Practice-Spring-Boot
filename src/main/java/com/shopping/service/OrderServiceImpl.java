package com.shopping.service;

import com.google.protobuf.util.Timestamps;
import com.shopping.db.Order;
import com.shopping.db.OrderDao;
import com.shopping.stubs.order.OrderRequest;
import com.shopping.stubs.order.OrderResponse;
import com.shopping.stubs.order.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {
    private Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());
    private OrderDao orderDao = new OrderDao();

    @Override
    public void getOrdersForUser(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        List<Order> orders = orderDao.getOrders(request.getUserId());
        logger.info("Got orders from OrderDao and convering to OrderResponse");
       List<com.shopping.stubs.order.Order> orderForUser = orders.stream().map(order -> com.shopping.stubs.order.Order.newBuilder()
                .setUserId(order.getUserId())
                .setOrderId(order.getOrderId())
                .setNoOfItems(order.getNoOfItems())
                .setTotalAmount(order.getTotalAmount())
                .setOrderDate(Timestamps.fromMillis(order.getOrderDate().getTime())).build()).collect(Collectors.toList());
        OrderResponse orderResponse = OrderResponse.newBuilder().addAllOrder(orderForUser).build();
        responseObserver.onNext(orderResponse);
        responseObserver.onCompleted();
    }

}

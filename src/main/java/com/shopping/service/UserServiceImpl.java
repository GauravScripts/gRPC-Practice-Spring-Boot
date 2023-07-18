package com.shopping.service;

import com.shopping.client.OrderClient;
import com.shopping.db.User;
import com.shopping.db.UserDao;
import com.shopping.stubs.order.Order;
import com.shopping.stubs.user.Gender;
import com.shopping.stubs.user.UserRequest;
import com.shopping.stubs.user.UserResponse;
import com.shopping.stubs.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    private Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
       private final UserDao userDao = new UserDao();
    @Override
    public void getUserDetails(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = userDao.getDetails(request.getUsername());
        UserResponse.Builder userResponseBuilder = UserResponse.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setName(user.getName())
                .setAge(user.getAge())
                .setGender(Gender.valueOf(user.getGender()));
        // get orders by invoking the Order client
        List<Order> orders = getOrders(userResponseBuilder);
        userResponseBuilder.setNoOfOrders(orders.size());
        UserResponse userResponse = userResponseBuilder.build();

        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();

    }

    private List<Order> getOrders(UserResponse.Builder userResponseBuilder) {
       logger.info("Creating and Invoking the Order client");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext().build();
        OrderClient orderClient = new OrderClient(channel);
        List<Order> orders = orderClient.getOrders(userResponseBuilder.getId());
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,"Channel did not shutdown", e);
        }
        return orders;
    }
}

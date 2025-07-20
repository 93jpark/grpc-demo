package com.example.userapiserver.service;

import com.example.grpc.*;
import com.example.userapiserver.domain.User;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.HashMap;
import java.util.Map;

@GrpcService
public class UserService extends UserGrpc.UserImplBase {

    private final Map<Integer, User> userRepository;
    private Integer incrementer = 0;

    public UserService() {
        HashMap<Integer, User> userRepository = new HashMap<>();
        userRepository.put(1, new User(1, "john_doe", "john.doe@gmail.com"));
        this.userRepository = userRepository;
        incrementer++;
    }

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = new User(++this.incrementer, request.getUsername(), request.getEmail());
        UserResponse response = UserResponse.newBuilder()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserInfo(GetUserInfoRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = userRepository.get(request.getUserId());
        UserResponse response = UserResponse.newBuilder()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UpdateUserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = userRepository.get(request.getUserId());
        if (user == null) {
            responseObserver.onError(new RuntimeException("User not found"));
            return;
        }
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        UserResponse response = UserResponse.newBuilder()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUser(DeleteUserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = userRepository.get(request.getUserId());
        if (user == null) {
            responseObserver.onError(new RuntimeException("User not found"));
            return;
        }
        userRepository.remove(request.getUserId());
        UserResponse response = UserResponse.newBuilder()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}

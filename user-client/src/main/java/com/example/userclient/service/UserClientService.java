package com.example.userclient.service;

import com.example.grpc.CreateUserRequest;
import com.example.grpc.DeleteUserRequest;
import com.example.grpc.GetUserInfoRequest;
import com.example.grpc.UpdateUserRequest;
import com.example.grpc.UserGrpc;
import com.example.grpc.UserResponse;
import jakarta.annotation.PostConstruct;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class UserClientService implements UserService {

    @GrpcClient("user")
    private UserGrpc.UserBlockingStub stub;

    @Override
    public void createUser(String username, String email) {
        CreateUserRequest request = CreateUserRequest.newBuilder()
                .setUsername(username)
                .setEmail(email)
                .build();
        UserResponse response = stub.createUser(request);
        System.out.println(response);
    }

    @Override
    public void getUserInfo(Integer userId) {
        GetUserInfoRequest request = GetUserInfoRequest.newBuilder()
                .setUserId(1)
                .build();
        UserResponse response = stub.getUserInfo(request);
        System.out.println(response);
    }

    @Override
    public void updateUser(Integer userId, String username, String email) {
        UpdateUserRequest request = UpdateUserRequest.newBuilder()
                .setUsername("John.L.Doe")
                .setEmail("john_l_doe@gmail.com")
                .build();

        UserResponse response = stub.updateUser(request);
        System.out.println(response);
    }

    @Override
    public void deleteUser(Integer userId) {
        DeleteUserRequest request = DeleteUserRequest.newBuilder()
                .setUserId(1)
                .build();

        UserResponse response = stub.deleteUser(request);
        System.out.println(response);
    }

    public String getUserInfoAndGetMessage(Integer userId) {
        GetUserInfoRequest request = GetUserInfoRequest.newBuilder()
                .setUserId(userId)
                .build();
        UserResponse response = stub.getUserInfo(request);
        // 간단하게 json/문자열 형태로 변환 (실제 포맷은 필요에 맞게)
        return String.format("id:%d, name:%s, email:%s", response.getUserId(), response.getUsername(), response.getEmail());
    }


}

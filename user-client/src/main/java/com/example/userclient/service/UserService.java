package com.example.userclient.service;

public interface UserService {

    void createUser(String username, String email);

    void getUserInfo(int userId);

    void updateUser(int userId, String username, String email);

    void deleteUser(int userId);

}

package com.example.userclient.service;

public interface UserService {

    void createUser(String username, String email);

    void getUserInfo(Integer userId);

    void updateUser(Integer userId, String username, String email);

    void deleteUser(Integer userId);

}

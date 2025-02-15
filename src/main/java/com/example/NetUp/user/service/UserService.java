package com.example.NetUp.user.service;


import com.example.NetUp.user.dtos.UserDTOReq;
import com.example.NetUp.user.dtos.UserDTORes;

import java.util.List;

public interface UserService {
    UserDTORes createUser(UserDTOReq userDTOReq);
    UserDTORes getUserById(String id);
    List<UserDTORes> getAllUsers();
    UserDTORes updateUser(String id, UserDTOReq userDTOReq);
    void deleteUser(String id);
}
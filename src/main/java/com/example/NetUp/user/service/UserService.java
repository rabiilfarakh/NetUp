package com.example.NetUp.user.service;


import com.example.NetUp.user.dtos.UserDTOReq;
import com.example.NetUp.user.dtos.UserDTORes;

import java.util.List;

public interface UserService {
    UserDTORes createUser(UserDTOReq userDTOReq);
    UserDTORes getUserById(Long id);
    List<UserDTORes> getAllUsers();
    UserDTORes updateUser(Long id, UserDTOReq userDTOReq);
    void deleteUser(Long id);
}
package com.example.NetUp.user.service;

import com.example.NetUp.user.entities.User;
import com.example.NetUp.user.mapper.UserMapper;
import com.example.NetUp.user.repository.UserRepository;
import com.example.NetUp.user.dtos.UserDTOReq;
import com.example.NetUp.user.dtos.UserDTORes;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTORes createUser(UserDTOReq userDTOReq) {
        User user = userMapper.toEntity(userDTOReq);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDTORes getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDTORes> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)

                .collect(Collectors.toList());
    }
    @Override
    public UserDTORes updateUser(Long id, UserDTOReq userDTOReq) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUserFromDto(userDTOReq, existingUser);

        return userMapper.toDto(userRepository.save(existingUser));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

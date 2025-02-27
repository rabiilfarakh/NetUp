package com.example.NetUp.user.service;

import com.example.NetUp.community.entities.Community;
import com.example.NetUp.community.repository.CommunityRepository;
import com.example.NetUp.user.Role;
import com.example.NetUp.user.entities.User;
import com.example.NetUp.user.mapper.UserMapper;
import com.example.NetUp.user.repository.UserRepository;
import com.example.NetUp.user.dtos.UserDTOReq;
import com.example.NetUp.user.dtos.UserDTORes;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, CommunityRepository communityRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTORes createUser(UserDTOReq userDTOReq) {
        User user = userMapper.toEntity(userDTOReq);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Community community = communityRepository.findById(userDTOReq.community_id())
                .orElseThrow(() -> new RuntimeException("Community not found with id: " + userDTOReq.community_id()));

        user.setCommunity(community);
        User savedUser = userRepository.save(user);
        Long userCount = communityRepository.countUsersInCommunity(userDTOReq.community_id());
        community.setQuantity(userCount);
        communityRepository.save(community);

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

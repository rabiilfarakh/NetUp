package com.example.NetUp.user;

import com.example.NetUp.community.entities.Community;
import com.example.NetUp.community.repository.CommunityRepository;
import com.example.NetUp.user.dtos.UserDTOReq;
import com.example.NetUp.user.dtos.UserDTORes;
import com.example.NetUp.user.entities.User;
import com.example.NetUp.user.mapper.UserMapper;
import com.example.NetUp.user.repository.UserRepository;
import com.example.NetUp.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommunityRepository communityRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTOReq userDTOReq;
    private UserDTORes userDTORes;
    private Community community;

    @BeforeEach
    void setUp() {
        community = new Community();
        community.setId(1L);
        community.setQuantity(10L);

        user = User.builder()
                .id(1L)
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .birthday(LocalDate.of(2000, 1, 1))
                .address("123 Street")
                .experience(5)
                .location("Paris")
                .photo("photo.jpg")
                .role(Role.USER)
                .community(community)
                .build();

        userDTOReq = new UserDTOReq(
                "testUser",
                "test@example.com",
                LocalDate.of(2000, 1, 1),
                "password",
                "123 Street",
                5,
                "Paris",
                "photo.jpg",
                1L
        );

        userDTORes = new UserDTORes(
                1L,
                "testUser",
                "test@example.com",
                LocalDate.of(2000, 1, 1),
                "123 Street",
                5,
                "Paris",
                "photo.jpg",
                Role.USER,
                null
        );
    }

    @Test
    void createUser_ShouldReturnUser_WhenCommunityExists() {
        when(userMapper.toEntity(userDTOReq)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTORes);
        when(communityRepository.countUsersInCommunity(1L)).thenReturn(11L);

        UserDTORes result = userService.createUser(userDTOReq);

        assertNotNull(result);
        assertEquals("testUser", result.username());
        verify(userRepository).save(any(User.class));
        verify(communityRepository).save(any(Community.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenCommunityDoesNotExist() {
        when(communityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDTOReq);
        });

        assertTrue(exception.getMessage().contains("Community not found"));
        verify(userRepository, never()).save(any());
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTORes);

        UserDTORes result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("testUser", result.username());
        assertEquals(1L, result.id());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserById(1L));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getUserByUsername_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTORes);

        UserDTORes result = userService.getUserByUsername("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.username());
    }

    @Test
    void getUserByUsername_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserByUsername("testUser"));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateUserFromDto(userDTOReq, user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTORes);

        UserDTORes result = userService.updateUser(1L, userDTOReq);

        assertNotNull(result);
        assertEquals("testUser", result.username());
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUser(1L, userDTOReq));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).deleteById(any());
    }
}
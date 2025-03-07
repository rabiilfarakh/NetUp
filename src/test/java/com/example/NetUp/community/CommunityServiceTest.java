package com.example.NetUp.community.service;

import com.example.NetUp.community.entities.Community;
import com.example.NetUp.community.mapper.CommunityMapper;
import com.example.NetUp.community.repository.CommunityRepository;
import com.example.NetUp.community.dtos.CommunityDTOReq;
import com.example.NetUp.community.dtos.CommunityDTORes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommunityServiceTest {

    @Mock
    private CommunityRepository communityRepository;

    @Mock
    private CommunityMapper communityMapper;

    @InjectMocks
    private CommunityServiceImpl communityService;

    private Community community;
    private CommunityDTOReq communityDTOReq;
    private CommunityDTORes communityDTORes;

    @BeforeEach
    void setUp() {
        community = new Community();
        community.setId(1L);
        community.setName("Test Community");
        community.setDescription("Description of the Test Community");


        communityDTOReq = new CommunityDTOReq("Test Community", "Description of the Test Community");
        communityDTORes = new CommunityDTORes(1L, "Test Community", "Description of the Test Community", 10L);
    }

    @Test
    void createCommunity_ShouldReturnCommunityDTORes_WhenSuccessful() {

        when(communityMapper.toEntity(communityDTOReq)).thenReturn(community);
        when(communityRepository.save(community)).thenReturn(community);
        when(communityMapper.toDto(community)).thenReturn(communityDTORes);

        CommunityDTORes result = communityService.createCommunity(communityDTOReq);

        assertNotNull(result);
        assertEquals("Test Community", result.name());
        assertEquals("Description of the Test Community", result.description());
        verify(communityRepository).save(community);
    }

    @Test
    void getCommunityById_ShouldReturnCommunityDTORes_WhenCommunityExists() {
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));
        when(communityRepository.countUsersInCommunity(1L)).thenReturn(10L);
        when(communityMapper.toDto(community)).thenReturn(communityDTORes);

        CommunityDTORes result = communityService.getCommunityById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Community", result.name());
        assertEquals(10L, result.quantity());
    }

    @Test
    void getCommunityById_ShouldThrowException_WhenCommunityDoesNotExist() {
        when(communityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> communityService.getCommunityById(1L));
        assertEquals("Community not found", exception.getMessage());
    }

    @Test
    void updateCommunity_ShouldThrowException_WhenCommunityDoesNotExist() {
        when(communityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> communityService.updateCommunity(1L, communityDTOReq));
        assertEquals("Community not found", exception.getMessage());
    }


}

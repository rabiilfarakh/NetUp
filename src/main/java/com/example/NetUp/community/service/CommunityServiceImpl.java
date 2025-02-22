package com.example.NetUp.community.service;

import com.example.NetUp.community.entities.Community;
import com.example.NetUp.community.mapper.CommunityMapper;
import com.example.NetUp.community.repository.CommunityRepository;
import com.example.NetUp.community.dtos.CommunityDTOReq;
import com.example.NetUp.community.dtos.CommunityDTORes;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;

    public CommunityServiceImpl(CommunityRepository communityRepository, CommunityMapper communityMapper) {
        this.communityRepository = communityRepository;
        this.communityMapper = communityMapper;
    }

    @Override
    public CommunityDTORes createCommunity(CommunityDTOReq communityDTOReq) {
        Community community = communityMapper.toEntity(communityDTOReq);
        Community savedCommunity = communityRepository.save(community);
        return communityMapper.toDto(savedCommunity);
    }

    @Override
    public CommunityDTORes getCommunityById(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        return communityMapper.toDto(community);
    }

    @Override
    public List<CommunityDTORes> getAllCommunities() {
        return communityRepository.findAll().stream()
                .map(communityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommunityDTORes updateCommunity(Long id, CommunityDTOReq communityDTOReq) {
        Community existingCommunity = communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        communityMapper.updateCommunityFromDto(communityDTOReq, existingCommunity);
        return communityMapper.toDto(communityRepository.save(existingCommunity));
    }

    @Override
    public void deleteCommunity(Long id) {
        communityRepository.deleteById(id);
    }
}

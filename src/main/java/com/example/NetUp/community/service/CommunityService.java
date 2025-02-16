package com.example.NetUp.community.service;

import com.example.NetUp.community.dtos.CommunityDTOReq;
import com.example.NetUp.community.dtos.CommunityDTORes;

import java.util.List;

public interface CommunityService {
    CommunityDTORes createCommunity(CommunityDTOReq communityDTOReq);
    CommunityDTORes getCommunityById(Long id);
    List<CommunityDTORes> getAllCommunities();
    CommunityDTORes updateCommunity(Long id, CommunityDTOReq communityDTOReq);
    void deleteCommunity(Long id);
}

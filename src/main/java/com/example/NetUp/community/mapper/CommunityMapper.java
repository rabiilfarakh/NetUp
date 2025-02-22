package com.example.NetUp.community.mapper;

import com.example.NetUp.community.dtos.CommunityDTOReq;
import com.example.NetUp.community.dtos.CommunityDTORes;
import com.example.NetUp.community.entities.Community;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommunityMapper {
    CommunityMapper INSTANCE = Mappers.getMapper(CommunityMapper.class);

    CommunityDTORes toDto(Community community);

    Community toEntity(CommunityDTOReq communityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommunityFromDto(CommunityDTOReq communityDTOReq, @MappingTarget Community community);
}

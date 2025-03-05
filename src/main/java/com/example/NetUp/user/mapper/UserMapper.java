package com.example.NetUp.user.mapper;

import com.example.NetUp.user.dtos.UserDTOReq;
import com.example.NetUp.user.dtos.UserDTORes;
import com.example.NetUp.user.entities.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "community.id", target = "community.id")
    UserDTORes toDto(User user);

    @Mapping(source = "community_id", target = "community.id")
    User toEntity(UserDTOReq userDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDTOReq userDTOReq, @MappingTarget User user);
}
package com.example.NetUp.user.mapper;

import com.example.NetUp.user.dtos.UserDTOReq;
import com.example.NetUp.user.dtos.UserDTORes;
import com.example.NetUp.user.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTORes toDto(User user);

    User toEntity(UserDTOReq userDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDTOReq userDTOReq, @MappingTarget User user);
}

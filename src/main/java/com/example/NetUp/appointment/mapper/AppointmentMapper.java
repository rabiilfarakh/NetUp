package com.example.NetUp.appointment.mapper;

import com.example.NetUp.appointment.dtos.AppointmentDTOReq;
import com.example.NetUp.appointment.dtos.AppointmentDTORes;
import com.example.NetUp.appointment.entities.Appointment;
import com.example.NetUp.user.mapper.UserMapper;

import com.example.NetUp.user_appointment.AppointmentStatus;
import com.example.NetUp.user_appointment.dtos.UserAppointmentDTORes;
import com.example.NetUp.user_appointment.entites.UserAppointment;
import org.mapstruct.*;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AppointmentMapper {
    @Mapping(target = "participants", source = "userAppointments", qualifiedByName = "mapParticipants")
    AppointmentDTORes toDto(Appointment appointment);

    @Mapping(target = "userAppointments", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Appointment toEntity(AppointmentDTOReq appointmentDTOReq);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    UserAppointmentDTORes toUserAppointmentDto(UserAppointment userAppointment);

    @Named("mapParticipants")
    default Set<UserAppointmentDTORes> mapParticipants(Set<UserAppointment> userAppointments) {
        if (userAppointments == null) return null;
        return userAppointments.stream()
                .map(this::toUserAppointmentDto)
                .collect(Collectors.toSet());
    }

    @Named("statusToString")
    default String statusToString(AppointmentStatus status) {
        return status != null ? status.name() : null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAppointmentFromDto(AppointmentDTOReq appointmentDTOReq, @MappingTarget Appointment appointment);
}
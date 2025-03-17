package com.example.NetUp.appointment.dtos;

import com.example.NetUp.user.dtos.UserDTORes;
import com.example.NetUp.user_appointment.dtos.UserAppointmentDTORes;

import java.time.LocalDateTime;
import java.util.Set;

public record AppointmentDTORes(
        Long id,
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        UserDTORes creator,
        Set<UserAppointmentDTORes> participants
) {}

package com.example.NetUp.user_appointment.dtos;

import com.example.NetUp.user.dtos.UserDTORes;

public record UserAppointmentDTORes(
        Long id,
        UserDTORes user,
        String status
) {}

package com.example.NetUp.appointment.dtos;


import java.time.LocalDateTime;

public record AppointmentDTOReq(
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long creatorId
) {}
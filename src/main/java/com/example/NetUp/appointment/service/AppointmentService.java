package com.example.NetUp.appointment.service;

import com.example.NetUp.appointment.dtos.AppointmentDTOReq;
import com.example.NetUp.appointment.entities.Appointment;

public interface AppointmentService {
    Appointment createAppointment(AppointmentDTOReq appointmentDTOReq);
    Appointment joinAppointment(Long appointmentId, Long userId);
    Appointment approveUser(Long appointmentId, Long userId, Long creatorId);
}

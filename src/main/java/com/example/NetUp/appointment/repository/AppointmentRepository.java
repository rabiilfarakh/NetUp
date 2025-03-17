package com.example.NetUp.appointment.repository;

import com.example.NetUp.appointment.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}

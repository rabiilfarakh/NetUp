package com.example.NetUp.user_appointment.repository;

import com.example.NetUp.user_appointment.entites.UserAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAppointmentRepository extends JpaRepository<UserAppointment, Long> {
}

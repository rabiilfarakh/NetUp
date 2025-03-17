package com.example.NetUp.user_appointment.entites;

import com.example.NetUp.user_appointment.AppointmentStatus;
import com.example.NetUp.appointment.entities.Appointment;
import com.example.NetUp.user.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_appointments")
@NoArgsConstructor
@AllArgsConstructor
public class UserAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}
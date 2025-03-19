package com.example.NetUp.appointment.entities;

import com.example.NetUp.user.entities.User;
import com.example.NetUp.user_appointment.entites.UserAppointment;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "appointments")
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Set<UserAppointment> userAppointments = new HashSet<>();

    public void addUserAppointment(UserAppointment userAppointment) {
        this.userAppointments.add(userAppointment);
        userAppointment.setAppointment(this);
    }

    public void removeUserAppointment(UserAppointment userAppointment) {
        this.userAppointments.remove(userAppointment);
        userAppointment.setAppointment(null);
    }
}
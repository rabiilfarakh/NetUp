package com.example.NetUp.user.entities;

import com.example.NetUp.comment.entities.Comment;
import com.example.NetUp.community.entities.Community;
import com.example.NetUp.user.Role;
import com.example.NetUp.user_appointment.entites.UserAppointment;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private LocalDate birthday;
    private String password;
    private String address;
    private int experience;
    private String location;
    private String photo;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<UserAppointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();

    public void addAppointment(UserAppointment userAppointment) {
        this.appointments.add(userAppointment);
        userAppointment.setUser(this);
    }

    public void removeAppointment(UserAppointment userAppointment) {
        this.appointments.remove(userAppointment);
        userAppointment.setUser(null);
    }


}
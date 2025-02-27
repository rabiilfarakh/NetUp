package com.example.NetUp.user.entities;

import com.example.NetUp.community.entities.Community;
import com.example.NetUp.user.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
//@DynamicUpdate
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private String password;
    private String address;
    private String experience;
    private String location;
    private String photo;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "community_id")
    private Community community;

}
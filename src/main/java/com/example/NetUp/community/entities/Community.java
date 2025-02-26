package com.example.NetUp.community.entities;

import com.example.NetUp.user.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@DynamicUpdate
@Table(name = "communities")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Transient
    private Long quantity;

    @OneToMany(mappedBy = "community" , cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

}
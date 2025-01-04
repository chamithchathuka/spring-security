package com.lessons.spring_security.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Entity
@Builder
@Setter
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserInfo userInfo;
}

package com.javadeveloperzone.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Users
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Column(unique=true) //unique=true 해야함.
    @NotNull
    private String email;

    @Column(nullable=false) // Null이 되면 안됨.
    @NotNull
    private String role;

    private String provider;

    private String providerId;

    @CreationTimestamp // INSERT 쿼리 시 현재 시간으로 생성
    private LocalDateTime createDate= LocalDateTime.now();

    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedDate = LocalDateTime.now();

    @Builder
    public Users(String username, String password, String email, String role, String provider, String providerId, LocalDateTime createDate, LocalDateTime updatedDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }
}

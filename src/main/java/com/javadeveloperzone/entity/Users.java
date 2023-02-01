package com.javadeveloperzone.entity;

import com.javadeveloperzone.domain.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    @Length(min = 4, max = 100)
    private String password;

    @Column(unique=true) //unique=true 해야함.
    @Email
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp // INSERT 쿼리 시 현재 시간으로 생성
    private LocalDateTime createDate= LocalDateTime.now();

    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedDate = LocalDateTime.now();

    @OneToMany(mappedBy = "users")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Users(String password, String email, Role role, LocalDateTime createDate, LocalDateTime updatedDate) {
        this.password = password;
        this.email = email;
        this.role = role;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(role.getValue()));
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

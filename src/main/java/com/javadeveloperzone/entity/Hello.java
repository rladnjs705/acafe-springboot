package com.javadeveloperzone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class Hello {
    @Id
    private Long id;

    private String name;
}

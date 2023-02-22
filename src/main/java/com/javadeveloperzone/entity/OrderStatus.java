package com.javadeveloperzone.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    ORDER("N"), CANCEL("Y");
    private String value;
}

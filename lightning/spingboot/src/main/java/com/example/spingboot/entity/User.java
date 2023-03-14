package com.example.spingboot.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String sex;
    private int age;
    private String phone;
    private String email;
    private String address;
    private String avatar;
}

package com.example.kbfinal.entity;

import jakarta.persistence.*;
import lombok.Data;

// lombok 사용할것
@Entity
@Data
public class User {

    @Id
    private Long id;

    private String username;
    private String password;

    // 추가로 3개의 attribute 를 만들기
    private String email;
    private String address;
    private String phoneNumber;

}

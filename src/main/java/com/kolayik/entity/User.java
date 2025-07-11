package com.kolayik.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kolayik.utility.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbluser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    String address;
    @Column(nullable = false,length = 20)
    String phone;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false, length = 128)
    String password;
    String avatar;
    @Enumerated(EnumType.STRING)
    Status status;
    String companyName;
    Boolean emailVerified;
    @Column(name = "verification_token")
    String verificationToken;
    Long companyId;



}

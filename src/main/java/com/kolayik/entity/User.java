package com.kolayik.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kolayik.utility.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
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

    Status status;
    String companyName;
    Boolean emailVerified;

    @Column(name = "verification_token")
    String verificationToken;

    @OneToMany(mappedBy = "personnel", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<UserRole> roles;
}

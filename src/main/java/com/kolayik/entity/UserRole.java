package com.kolayik.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kolayik.utility.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tblrole")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)  // Enum string olarak DB'ye kaydolur
    @Column(nullable = false)
    Role roleName;

    Long userId;



}
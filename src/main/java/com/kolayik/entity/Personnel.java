package com.kolayik.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Personnel extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}

package com.kolayik.entity;

import com.kolayik.utility.enums.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tblmembership")
public class Membership {
    /**
     * Bu sayfa Üyelik plan sayfasıdır
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    Plan plan;
    LocalDateTime createdAt;
    String description;
    Double price;
    Integer userLimit;
    Long userId;



}

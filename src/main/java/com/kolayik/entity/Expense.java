package com.kolayik.entity;

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
@Table(name = "tblexpense")
public class Expense {
    /**
     * Bu sayfa Harcama sayfasıdır
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}

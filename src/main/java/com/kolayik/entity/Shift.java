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
@Table(name = "tblshift")
public class Shift {
    /**
     * Bu sayfa Vardiya sayfasıdır
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


}

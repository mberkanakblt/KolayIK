package com.kolayik.entity;

import com.kolayik.utility.enums.Status;
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
@Table(name = "tblexpense")
public class Expense {
    /**
     * Bu sayfa Harcama sayfasıdır
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double amount;
    String description;
    String fileUrl;
    LocalDateTime date;
    Status status;
    Long userId;
    String userName;
    Long companyId;
}

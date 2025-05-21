package com.kolayik.entity;

import com.kolayik.utility.enums.AllowState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tblallowmanage")
public class AllowManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long allow_id;
    Long user_id;
    String name;
    String surname;
    @Enumerated(EnumType.STRING)
    AllowState allowstate;
    LocalDate allowstartdate;
    LocalDate allowfinishdate;
    LocalDate approveddate;
    LocalDate rejecteddate;
    String allowtype;

}

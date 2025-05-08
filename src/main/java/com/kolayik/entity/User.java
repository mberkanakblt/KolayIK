package com.kolayik.entity;

import com.kolayik.utility.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    String ad;
    String adres;
    @Column(nullable = false,length = 20)
    String telefon;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false, length = 128)
    String sifre;
    String avatar;
    Status status;
    String roleName;
    String companyName;




    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.sifre;   }
}

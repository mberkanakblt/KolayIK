package com.kolayik.view;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kolayik.entity.Company;
import com.kolayik.utility.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VwUser {
    Long id;
    String name;
    String surname;

}

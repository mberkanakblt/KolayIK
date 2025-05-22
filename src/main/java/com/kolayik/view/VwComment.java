package com.kolayik.view;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class VwComment {
    String description;
    LocalDateTime commentDate;
    String companyName;
    String name;
    String surname;

}

package com.kolayik.view;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class VwComment {
    String description;
    LocalDateTime commentDate;
    String avatar;
    String name;

}

package com.kolayik.view;

import com.kolayik.utility.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VwManager {
    Long userId;
    String name;
    String surname;
    String email;
    Status status;
}

package com.kolayik.view;

import com.kolayik.utility.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VwCompany {
    String name;
    Status status;
    String userName;
    String email;


}

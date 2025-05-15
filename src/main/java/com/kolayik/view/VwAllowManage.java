package com.kolayik.view;

import com.kolayik.utility.enums.AllowState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@AllArgsConstructor
public class VwAllowManage {
    Long id;
    Long allow_id;
    Long user_id;
    String name;
    String surname;
    AllowState allowstate;
    LocalDate allowstartdate;
    LocalDate allowfinishdate;
    LocalDate approveddate;
    LocalDate rejecteddate;
    String allowtype;
}

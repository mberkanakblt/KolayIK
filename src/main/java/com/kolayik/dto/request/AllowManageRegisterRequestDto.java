package com.kolayik.dto.request;

import com.kolayik.utility.enums.AllowState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.Date;

public record AllowManageRegisterRequestDto(

        Long allow_id,
        Long user_id,
        String name,         // eklendi
        String surname ,
        @Enumerated(EnumType.STRING)
        AllowState allowstate,
        LocalDate allowstartdate,
        LocalDate allowfinishdate,
        LocalDate approveddate,
        LocalDate rejecteddate,
        String allowtype


) {
}

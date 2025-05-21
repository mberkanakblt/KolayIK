package com.kolayik.dto.request;

import com.kolayik.utility.enums.AllowState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Date;

public record AllowRegisterRequestDto(
       Long id, String allowtype
) {

    public AllowRegisterRequestDto
            (Long id, String allowtype){
        this.id=id;
        this.allowtype=allowtype;
    }

}

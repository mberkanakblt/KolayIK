package com.kolayik.dto.request;

import com.kolayik.utility.enums.AllowState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Setter;

public record AllowStateUpdateRequestDto(

      Long id,
    @Enumerated(EnumType.STRING)
    AllowState allowstate
) {





}

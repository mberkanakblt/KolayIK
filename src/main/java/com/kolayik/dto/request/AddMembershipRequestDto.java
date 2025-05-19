package com.kolayik.dto.request;

import com.kolayik.utility.enums.Plan;

import java.time.LocalDateTime;

public record AddMembershipRequestDto(
    String name,
    Plan plan,
    String description,
    Double price,
    Integer userLimit

) {
}

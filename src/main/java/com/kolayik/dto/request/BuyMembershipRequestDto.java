package com.kolayik.dto.request;

public record BuyMembershipRequestDto(
        String token,
        Long membershipId
) {
}

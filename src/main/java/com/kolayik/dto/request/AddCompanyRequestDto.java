package com.kolayik.dto.request;
public record AddCompanyRequestDto(
       // Long user_id,
    //    String token,
        String name,
        String address,
        String phone,
        String sector,
        String email
) {

}

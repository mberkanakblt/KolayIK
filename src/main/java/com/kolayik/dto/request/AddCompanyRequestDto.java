package com.kolayik.dto.request;
public record AddCompanyRequestDto(

        String token,
        String name,
        String address,
        String phone,
        String sector
) {

}

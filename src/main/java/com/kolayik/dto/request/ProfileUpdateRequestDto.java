package com.kolayik.dto.request;

import lombok.Data;

@Data
public class ProfileUpdateRequestDto {
    private String name;
    private String surname;
    private String phone;
    private String companyName;
    private String address;
    private String avatar;
    private String email;


}
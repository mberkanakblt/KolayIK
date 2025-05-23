package com.kolayik.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String companyName;
    private String address;
    private String avatar;
}
package com.md.ecommerce.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String mail;
}
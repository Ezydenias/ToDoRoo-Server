package com.Roo.demo.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    @Id
    private int id;
    private String username;
    private String password;
    private String repeatpassword;
    private String email;
}

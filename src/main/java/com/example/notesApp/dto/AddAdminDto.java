package com.example.notesApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAdminDto {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;
}

package com.example.notesApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangeDto {
    private String userName;
    @NotBlank
    private String password;
}

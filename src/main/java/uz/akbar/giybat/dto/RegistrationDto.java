package uz.akbar.giybat.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/** RegistrationDto */
@Data
public class RegistrationDto {

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Username required")
    private String username;

    @NotBlank(message = "Password required")
    private String password;
}

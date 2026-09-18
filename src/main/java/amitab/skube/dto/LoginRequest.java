package amitab.skube.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, message = "Password must be 6 character long") String password
) { }

package com.fittracker.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for creating or updating a user")
public record UserRequestDto(
        @NotBlank(message = "Name must not be blank")
        @Size(max = 100, message = "Name must be at most 100 characters")
        @Schema(description = "Username of the user", example = "john_doe")
        String name,
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid")
        @Schema(description = "Email address of the user", example = "john@example.com")
        String email
) {
}

package com.talentgrid.app.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactRequestDto(
    @NotBlank(message = "Name is mandatory")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    String name,

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "Subject is mandatory")
    @Size(min = 5, max = 150, message = "Subject must be between 5 and 150 characters")
    String subject,

    @NotBlank(message = "Message is mandatory")
    @Size(min = 5, max = 500, message = "Message must be between 5 and 500 characters")
    String message,

    @NotBlank(message = "UserType is mandatory")
    @Pattern(regexp = "Job Seeker|Employer|Other", message = "UserType must be either 'Job Seeker', 'Employer', or 'Other'")
    String userType
) implements Serializable {
    
}

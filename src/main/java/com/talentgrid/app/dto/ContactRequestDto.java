package com.talentgrid.app.dto;

import java.io.Serializable;

public record ContactRequestDto(
    String name,
    String email,
    String subject,
    String message,
    String userType
) implements Serializable {
    
}

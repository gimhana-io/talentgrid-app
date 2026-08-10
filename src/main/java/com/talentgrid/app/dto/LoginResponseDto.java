package com.talentgrid.app.dto;

public record LoginResponseDto(String message, UserDto user, String jwtToken) {

}

package com.gladysz.kidspartymanagerui.dto;

public record OrdererResponseDto(
        Long id, String firstName, String lastName,
        String email, String phone
) {}
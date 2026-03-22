package com.gladysz.kidspartymanagerui.dto;

public record AnimatorResponseDto(
        Long id, String firstName, String lastName,
        String email, String phone, boolean active
){}
package com.gladysz.kidspartymanagerui.dto;

public record OrdererCreateDto(
        String firstName, String lastName,
        String email, String phone
) {}
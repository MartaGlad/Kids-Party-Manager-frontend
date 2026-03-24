package com.gladysz.kidspartymanagerui.dto;

import jakarta.validation.constraints.NotBlank;

public record AnimatorCreateDto(
        String firstName, String lastName,
        String email, @NotBlank String phone
) {}

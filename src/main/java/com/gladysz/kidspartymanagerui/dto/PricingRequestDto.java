package com.gladysz.kidspartymanagerui.dto;


import java.time.LocalDate;

public record PricingRequestDto(
        Long eventPackageId, int childrenCount,
        LocalDate date
){}

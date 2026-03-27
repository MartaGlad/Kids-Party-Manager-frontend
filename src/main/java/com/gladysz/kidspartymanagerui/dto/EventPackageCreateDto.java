package com.gladysz.kidspartymanagerui.dto;

import java.math.BigDecimal;

public record EventPackageCreateDto(
        String name, String description, BigDecimal basePrice,
        Integer maxChildrenCount, Integer durationHr
){}

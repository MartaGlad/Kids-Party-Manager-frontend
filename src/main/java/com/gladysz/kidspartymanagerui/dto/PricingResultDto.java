package com.gladysz.kidspartymanagerui.dto;

import java.math.BigDecimal;

public record PricingResultDto(
        BigDecimal finalPricePln,
        BigDecimal priceInEur,
        BigDecimal priceInUsd,
        BigDecimal priceInGbp,
        boolean holiday
){}

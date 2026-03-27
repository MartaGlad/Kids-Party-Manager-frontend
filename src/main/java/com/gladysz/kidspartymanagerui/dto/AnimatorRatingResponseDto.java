package com.gladysz.kidspartymanagerui.dto;

public record AnimatorRatingResponseDto(
        Long animatorId,
        double averageRating,
        long ratingsCount
){}
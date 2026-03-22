package com.gladysz.kidspartymanagerui.dto;

import java.time.LocalDateTime;

public record ReservationCreateDto(
        Long eventPackageId, Long animatorId,
        Long ordererId, LocalDateTime eventDateTime,
        Integer childrenCount, Integer birthdayChildAge
) {}
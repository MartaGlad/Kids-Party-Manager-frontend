package com.gladysz.kidspartymanagerui.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationSummaryDto(
        Long id, String eventPackageName, String animatorName,
        LocalDateTime eventDateTime,
        int childrenCount, Status status, BigDecimal price
) {}
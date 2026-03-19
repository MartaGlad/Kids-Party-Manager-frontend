package com.gladysz.kidspartymanagerui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ReservationListItemDto {

    private Long id;
    private String eventPackageName;
    private String animatorName;
    private LocalDateTime eventDateTime;
    private int childrenCount;
    private Status status;
    private BigDecimal price;
}

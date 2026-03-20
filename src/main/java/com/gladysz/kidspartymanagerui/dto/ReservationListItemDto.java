package com.gladysz.kidspartymanagerui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (! (o instanceof ReservationListItemDto reservationListItemDto)) return false;

        return this.id != null && this.id.equals(reservationListItemDto.id);
    }


    @Override
    public int hashCode() {

        return this.id != null ? this.id.hashCode() : 0;
    }
}

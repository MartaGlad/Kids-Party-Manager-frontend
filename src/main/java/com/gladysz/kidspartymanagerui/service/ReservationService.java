package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.dto.ReservationListItemDto;
import com.gladysz.kidspartymanagerui.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {

    public List<ReservationListItemDto> getExampleReservations() {

        List<ReservationListItemDto> reservations = new ArrayList<>();

        reservations.add(new ReservationListItemDto(1L, "Package1", "Peter Paper",
                LocalDateTime.now().plusDays(18), 4, Status.NEW, BigDecimal.valueOf(700)));

        reservations.add(new ReservationListItemDto(2L, "Package5", "Alex Cat",
                LocalDateTime.now().plusDays(15), 6, Status.CONFIRMED, BigDecimal.valueOf(1100)));

        reservations.add(new ReservationListItemDto(3L, "Package2", "Peter Paper",
                LocalDateTime.now().minusDays(3), 5, Status.NEW, BigDecimal.valueOf(800)));


        return reservations;
    }
}





package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.client.ReservationClient;
import com.gladysz.kidspartymanagerui.dto.ReservationSummaryDto;
import com.gladysz.kidspartymanagerui.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationClient reservationClient;

    public List<ReservationSummaryDto> getReservations(Status status, LocalDate from, LocalDate to) {

        return reservationClient.getReservations(status, from, to);
    }
}





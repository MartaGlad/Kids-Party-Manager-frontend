package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.client.ReservationClient;
import com.gladysz.kidspartymanagerui.dto.ReservationListItemDto;
import com.gladysz.kidspartymanagerui.dto.ReservationResponseDto;
import com.gladysz.kidspartymanagerui.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationClient reservationClient;

    public List<ReservationListItemDto> getReservations(Status status, LocalDate from, LocalDate to) {

        List<ReservationResponseDto> response = reservationClient.getReservations(status, from, to);

        List<ReservationListItemDto> resultList = new ArrayList<>();

        for (ReservationResponseDto res : response) {
            resultList.add(new ReservationListItemDto(
                    res.id(), res.eventPackageId().toString(), res.animatorId().toString(),
                    res.eventDateTime(), res.childrenCount(), res.status(), res.priceSnapshot())
            );
        }
        return resultList;
    }
}





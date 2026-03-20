package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.client.ReservationClient;
import com.gladysz.kidspartymanagerui.dto.ReservationListItemDto;
import com.gladysz.kidspartymanagerui.dto.ReservationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationClient reservationClient;

    public List<ReservationListItemDto> getReservations() {

        List<ReservationResponseDto> response = reservationClient
                .getReservations(null, null, null);

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





package com.gladysz.kidspartymanagerui.client;

import com.gladysz.kidspartymanagerui.dto.ReservationCreateDto;
import com.gladysz.kidspartymanagerui.dto.ReservationResponseDto;
import com.gladysz.kidspartymanagerui.dto.ReservationSummaryDto;
import com.gladysz.kidspartymanagerui.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
public class ReservationClient {

    private final RestClient restClient;

    public List<ReservationSummaryDto> getReservations(Status status, LocalDate from, LocalDate to) {

        return restClient.get()
                .uri(uriBuilder -> {
                    UriBuilder builder = uriBuilder.path("/api/v1/reservations");

                    if (status != null) {
                        builder.queryParam("status", status);
                    }
                    if (from != null) {
                        builder.queryParam("from", from);
                    }
                    if (to != null) {
                        builder.queryParam("to", to);
                    }
                    return builder.build();
                })
                .retrieve()
                .body(new ParameterizedTypeReference<List<ReservationSummaryDto>>() {});
    }


    public void createReservation(ReservationCreateDto reservationCreateDto) {

        restClient.post()
                .uri("/api/v1/reservations")
                .body(reservationCreateDto)
                .retrieve()
                .body(ReservationResponseDto.class);
    }
}

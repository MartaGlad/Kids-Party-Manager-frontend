package com.gladysz.kidspartymanagerui.client;

import com.gladysz.kidspartymanagerui.dto.ReservationResponseDto;
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

    public List<ReservationResponseDto> getReservations(Status status, LocalDate from, LocalDate to) {

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
                .body(new ParameterizedTypeReference<List<ReservationResponseDto>>(){});
    }
}

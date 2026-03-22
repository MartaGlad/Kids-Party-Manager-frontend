package com.gladysz.kidspartymanagerui.client;

import com.gladysz.kidspartymanagerui.dto.EventPackageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


@Component
@RequiredArgsConstructor
public class EventPackageClient {

    private final RestClient restClient;

    public List<EventPackageResponseDto> getEventPackages() {

        return restClient.get()
                .uri("/api/v1/event-packages")
                .retrieve()
                .body(new ParameterizedTypeReference<List<EventPackageResponseDto>>() {});
    }
}

package com.gladysz.kidspartymanagerui.client;

import com.gladysz.kidspartymanagerui.dto.EventPackageCreateDto;
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

    public List<EventPackageResponseDto> getAllEventPackages() {

        return restClient.get()
                .uri("/api/v1/event-packages")
                .retrieve()
                .body(new ParameterizedTypeReference<List<EventPackageResponseDto>>() {});
    }


    public EventPackageResponseDto createEventPackage(EventPackageCreateDto eventPackageCreateDto) {

        return restClient.post()
                .uri("/api/v1/event-packages")
                .body(eventPackageCreateDto)
                .retrieve()
                .body(EventPackageResponseDto.class);
    }
}

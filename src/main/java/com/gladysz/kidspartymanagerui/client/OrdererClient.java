package com.gladysz.kidspartymanagerui.client;

import com.gladysz.kidspartymanagerui.dto.OrdererResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrdererClient {

    private final RestClient restClient;

    public List<OrdererResponseDto> getOrderers() {

        return restClient.get()
                .uri("/api/v1/orderers")
                .retrieve()
                .body(new ParameterizedTypeReference<List<OrdererResponseDto>>() {});
    }
}

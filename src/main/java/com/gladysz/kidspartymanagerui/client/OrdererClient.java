package com.gladysz.kidspartymanagerui.client;

import com.gladysz.kidspartymanagerui.dto.OrdererCreateDto;
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

    public List<OrdererResponseDto> getAllOrderers() {

        return restClient.get()
                .uri("/api/v1/orderers")
                .retrieve()
                .body(new ParameterizedTypeReference<List<OrdererResponseDto>>() {});
    }


    public OrdererResponseDto createOrderer(OrdererCreateDto ordererCreateDto) {

        return restClient.post()
                .uri("/api/v1/orderers")
                .body(ordererCreateDto)
                .retrieve()
                .body(OrdererResponseDto.class);
    }


    public OrdererResponseDto updateOrderer(Long id, OrdererCreateDto ordererCreateDto) {

        return restClient.patch()
                .uri("/api/v1/orderers/{id}", id)
                .body(ordererCreateDto)
                .retrieve()
                .body(OrdererResponseDto.class);
    }


    public void deleteOrderer(Long id) {

        restClient.delete()
                .uri("/api/v1/orderers/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

}

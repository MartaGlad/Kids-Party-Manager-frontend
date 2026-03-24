package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.client.OrdererClient;
import com.gladysz.kidspartymanagerui.dto.OrdererCreateDto;
import com.gladysz.kidspartymanagerui.dto.OrdererResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdererService {

    private final OrdererClient ordererClient;

    public List<OrdererResponseDto> getAllOrderers() {

        return ordererClient.getAllOrderers();
    }


    public OrdererResponseDto createOrderer(OrdererCreateDto ordererCreateDto) {

        return ordererClient.createOrderer(ordererCreateDto);
    }


    public OrdererResponseDto updateOrderer(Long id, OrdererCreateDto ordererCreateDto) {

        return ordererClient.updateOrderer(id, ordererCreateDto);
    }


    public void deleteOrderer(Long id) {

        ordererClient.deleteOrderer(id);
    }
}

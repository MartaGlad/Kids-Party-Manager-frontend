package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.client.OrdererClient;
import com.gladysz.kidspartymanagerui.dto.OrdererResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdererService {

    private final OrdererClient ordererClient;

    public List<OrdererResponseDto> getOrderers() {

        return ordererClient.getOrderers();
    }
}

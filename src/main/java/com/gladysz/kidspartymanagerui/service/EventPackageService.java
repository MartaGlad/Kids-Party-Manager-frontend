package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.client.EventPackageClient;
import com.gladysz.kidspartymanagerui.dto.EventPackageCreateDto;
import com.gladysz.kidspartymanagerui.dto.EventPackageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventPackageService {

    private final EventPackageClient eventPackageClient;

    public List<EventPackageResponseDto> getAllEventPackages() {

        return eventPackageClient.getAllEventPackages();
    }


    public EventPackageResponseDto createEventPackage(EventPackageCreateDto eventPackageCreateDto) {

        return eventPackageClient.createEventPackage(eventPackageCreateDto);
    }
}
package com.gladysz.kidspartymanagerui.client;

import com.gladysz.kidspartymanagerui.dto.PricingRequestDto;
import com.gladysz.kidspartymanagerui.dto.PricingResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class PricingPreviewClient {

    private final RestClient restClient;

    public PricingResultDto getPreviewPrice(PricingRequestDto pricingRequestDto) {

        return restClient.post()
                .uri("/api/v1/pricing/preview")
                .body(pricingRequestDto)
                .retrieve()
                .body(PricingResultDto.class);
    }
}

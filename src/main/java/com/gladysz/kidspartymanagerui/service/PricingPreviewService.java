package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.client.PricingPreviewClient;
import com.gladysz.kidspartymanagerui.dto.PricingRequestDto;
import com.gladysz.kidspartymanagerui.dto.PricingResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingPreviewService {

    private final PricingPreviewClient pricingPreviewClient;

    public PricingResultDto getPricingPreview(PricingRequestDto pricingRequestDto) {

        return pricingPreviewClient.getPreviewPrice(pricingRequestDto);
    }
}

package com.gladysz.kidspartymanagerui.client;

import com.gladysz.kidspartymanagerui.dto.AnimatorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnimatorClient {

    private final RestClient restClient;

    public List<AnimatorResponseDto> getAnimators() {

        return restClient.get()
                .uri("/api/v1/animators")
                .retrieve()
                .body(new ParameterizedTypeReference<List<AnimatorResponseDto>>() {});
    }
}

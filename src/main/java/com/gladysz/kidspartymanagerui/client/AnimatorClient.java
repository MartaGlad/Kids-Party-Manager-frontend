package com.gladysz.kidspartymanagerui.client;

import com.gladysz.kidspartymanagerui.dto.AnimatorCreateDto;
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

    public List<AnimatorResponseDto> getAllAnimators() {

        return restClient.get()
                .uri("/api/v1/animators")
                .retrieve()
                .body(new ParameterizedTypeReference<List<AnimatorResponseDto>>() {});
    }


    public AnimatorResponseDto createAnimator(AnimatorCreateDto animatorCreateDto) {

        return restClient.post()
                .uri("/api/v1/animators")
                .body(animatorCreateDto)
                .retrieve()
                .body(AnimatorResponseDto.class);
    }


    public AnimatorResponseDto updateAnimator(Long id, AnimatorCreateDto animatorCreateDto) {

        return restClient.patch()
                .uri("/api/v1/animators/{id}", id)
                .body(animatorCreateDto)
                .retrieve()
                .body(AnimatorResponseDto.class);
    }


    public void deleteAnimator(Long id) {

        restClient.delete()
                .uri("/api/v1/animators/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}

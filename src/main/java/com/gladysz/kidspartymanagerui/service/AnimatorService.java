package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.client.AnimatorClient;
import com.gladysz.kidspartymanagerui.dto.AnimatorCreateDto;
import com.gladysz.kidspartymanagerui.dto.AnimatorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimatorService {

    private final AnimatorClient animatorClient;

    public List<AnimatorResponseDto> getAllAnimators() {

        return animatorClient.getAllAnimators();
    }


    public AnimatorResponseDto createAnimator(AnimatorCreateDto animatorCreateDto) {

        return animatorClient.createAnimator(animatorCreateDto);
    }


    public AnimatorResponseDto updateAnimator(Long id, AnimatorCreateDto animatorCreateDto) {

        return animatorClient.updateAnimator(id, animatorCreateDto);
    }


    public void deleteAnimator(Long id) {

        animatorClient.deleteAnimator(id);
    }
}

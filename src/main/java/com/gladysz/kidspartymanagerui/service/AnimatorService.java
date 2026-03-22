package com.gladysz.kidspartymanagerui.service;

import com.gladysz.kidspartymanagerui.client.AnimatorClient;
import com.gladysz.kidspartymanagerui.dto.AnimatorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimatorService {

    private final AnimatorClient animatorClient;

    public List<AnimatorResponseDto> getAnimators() {

        return animatorClient.getAnimators();
    }
}

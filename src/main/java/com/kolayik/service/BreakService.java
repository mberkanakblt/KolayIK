package com.kolayik.service;

import com.kolayik.dto.request.BreakRequestDto;
import com.kolayik.dto.request.BreakUpdateDto;
import com.kolayik.dto.response.BreakResponseDto;
import com.kolayik.entity.Break;
import com.kolayik.entity.User;
import com.kolayik.repository.BreakRepository;
import com.kolayik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class BreakService {

    private final BreakRepository breakRepository;
    private final UserRepository userRepository;

    public Break addBreak(BreakRequestDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Break newBreak = Break.builder()
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .description(dto.description())
                .user(user)
                .build();

        return breakRepository.save(newBreak);
    }



    public void deleteBreak(Long id) {
        Break delete = breakRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Silinecek mola bulunamadı."));

        breakRepository.delete(delete);
    }









    public Break updateBreak(Long id, BreakUpdateDto dto) {
        Break update = breakRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek mola bulunamadı."));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));


        update.setStartTime(dto.startTime());
        update.setEndTime(dto.endTime());
        update.setDescription(dto.description());
        update.setUser(user);

        return breakRepository.save(update);
    }

    public List<BreakResponseDto> getBreakDtoByUserId(Long userId) {
        return breakRepository.findAllByUserId(userId).stream()
                .map(b -> BreakResponseDto.builder()
                        .id(b.getId())
                        .startTime(b.getStartTime())
                        .endTime(b.getEndTime())
                        .description(b.getDescription())
                        .userFullName(b.getUser().getName() + " " + b.getUser().getSurname())
                        .build())
                .toList();
    }



}


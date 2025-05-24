package com.kolayik.service;

import com.kolayik.dto.request.RegisterShiftRequestDto;
import com.kolayik.entity.Shift;
import com.kolayik.entity.User;

import com.kolayik.repository.ShiftRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.view.VwAllowManage;
import com.kolayik.view.VwShift;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final UserRepository userRepository;


    public void doRegister( RegisterShiftRequestDto dto ) {

       Shift shift = Shift.builder()
               .userId(dto.userId())
               .startDate(dto.startDate())
               .startTime(dto.startTime())
               .endTime(dto.endTime())
               .totalWorkTime(dto.totalWorkTime())
               .isRepeat(dto.isRepeat())
               .days(dto.days())
                              .build();


        shiftRepository.save(shift);

    }



    public void deleteShift(Long id) {

        shiftRepository.deleteById(id);
    }

    public List<VwShift> getAllShift() {
        return shiftRepository.getAllShifts();
    }

    public void update(RegisterShiftRequestDto dto, Long id) {

        Shift existingShift = shiftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID " + id + " ile vardiya bulunamadı."));

        // Alanları güncelle
        existingShift.setUserId(dto.userId());
        existingShift.setStartDate(dto.startDate());
        existingShift.setStartTime(dto.startTime());
        existingShift.setEndTime(dto.endTime());
        existingShift.setTotalWorkTime(dto.totalWorkTime());
        existingShift.setIsRepeat(dto.isRepeat());
        existingShift.setDays(dto.days());

        // Değişiklikleri kaydet
        shiftRepository.save(existingShift);
    }
}

package com.kolayik.service;

import com.kolayik.dto.request.AllowManageRegisterRequestDto;
import com.kolayik.entity.Allow;
import com.kolayik.entity.AllowManage;
import com.kolayik.entity.User;
import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.AllowManageRepository;
import com.kolayik.repository.AllowRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.utility.enums.AllowState;
import com.kolayik.view.VwAllowManage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AllowManageService {
    private final AllowManageRepository allowManageRepository;
    private final UserRepository userRepository;
    private final AllowRepository allowRepository;

    public void allowManageRegister(AllowManageRegisterRequestDto dto) {


//       AllowManage allowmanage = AllowManage.builder()
//               .allow_id(dto.allow_id())
//               .user_id(dto.user_id())
//               .name(dto.name())
//               .surname(dto.surname())
//               .allowstate(dto.allowstate())
//               .allowstartdate(dto.allowstartdate())
//               .allowfinishdate(dto.allowfinishdate())
//               .approveddate(dto.approveddate())
//               .rejecteddate(dto.rejecteddate())
//               .allowtype(dto.allowtype())
//                .build();


//
        Optional<User> userOpt = userRepository.findByNameAndSurname(dto.name(), dto.surname());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }
        User user = userOpt.get();

        Optional<Allow> allowOpt = allowRepository.findByAllowtype(dto.allowtype());
        if (allowOpt.isEmpty()) {
            throw new RuntimeException("İzin türü bulunamadı");
        }
        Allow allow = allowOpt.get();

        AllowManage allowmanage = new AllowManage();
        allowmanage.setAllow_id(allow.getId());
        allowmanage.setUser_id(allow.getId());
        allowmanage.setName(dto.name());
        allowmanage.setSurname(dto.surname());
        allowmanage.setAllowstartdate(dto.allowstartdate());
        allowmanage.setAllowfinishdate(dto.allowfinishdate());
        allowmanage.setApproveddate(null);
        allowmanage.setRejecteddate(null);
        allowmanage.setAllowtype(dto.allowtype());
        allowmanage.setAllowstate(AllowState.BEKLEMEDE);

        allowManageRepository.save(allowmanage);


//        Allow allow = new Allow();
//        allow.setUser_id(dto.user_id());
//        allow.setAllowstartdate(dto.allowstartdate());
//        allow.setAllowfinishdate(dto.allowfinishdate());
//        allow.setAllowtype(dto.allowtype());
//        allow.setAllowstate(AllowState.BEKLEMEDE); // yeni izinler genelde beklemede başlar
//        // diğer alanları set et
//


//        // 1. Kullanıcı var mı kontrol et
//        Optional<User> userOpt = userRepository.findById(dto.user_id());
//        if (userOpt.isEmpty()) {
//            throw new KolayIkException(ErrorType.USER_NOT_FOUND);
//        }
//
//        if (dto.allowstartdate().after(dto.allowfinishdate())) {
//            throw new KolayIkException(ErrorType.INVALID_DATE_RANGE);
//        }
//
//        // 3. Aynı tarih aralığında başka izin var mı kontrolü (isteğe bağlı)
//        boolean hasOverlappingAllow = allowRepository.existsByUserIdAndDateRange(
//                dto.user_id(), dto.allowstartdate(), dto.allowfinishdate());
//        if (hasOverlappingAllow) {
//            throw new KolayIkException(ErrorType.ALLOW_DATE_CONFLICT);
//        }
//
//        // 4. İzin kaydet
//        Allow allow = new Allow();
//        allow.setUser_id(dto.user_id());
//        allow.setAllowstartdate(dto.allowstartdate());
//        allow.setAllowfinishdate(dto.allowfinishdate());
//        allow.setAllowtype(dto.allowtype());
//        allow.setAllowstate(AllowState.BEKLEMEDE); // yeni izinler genelde beklemede başlar
//        // diğer alanları set et
//
//        allowRepository.save(allow);


    }


    public Optional<User> findByNameAndSurname(AllowManageRegisterRequestDto dto) {
        return allowManageRepository.findOptionalByNameAndSurname(dto.name(), dto.surname());
    }

    public List<VwAllowManage> getAllAllowsManage() {
        return allowManageRepository.getAllAllowManage();
    }

    public List<VwAllowManage> getRequestAllowsManage() {

        List<VwAllowManage> pendingAllowsManage = allowManageRepository.getAllPendingAllows();

        if (pendingAllowsManage.isEmpty()) {
            throw new KolayIkException(ErrorType.ALLOW_NOT_PENDING);
        }

        return pendingAllowsManage;

    }

    public boolean updateAllowState(Long id, AllowState allowState) {
        Optional<AllowManage> optionalAllow = allowManageRepository.findById(id);

        if (optionalAllow.isPresent()) {
            AllowManage allow = optionalAllow.get();

            allow.setAllowstate(allowState);

            if (allowState == AllowState.ONAYLANDI) {
                allow.setApproveddate(LocalDate.now());
            } else if (allowState == AllowState.REDDEDILDI) {
                allow.setRejecteddate(LocalDate.now());
            }

            allowManageRepository.save(allow);
            return true;
        }

        return false;
    }

        //Optional<User> userOpt = userRepository.findById(userId);
        //userOpt.ifPresent(user -> {
        //    String name = user.getName();
        //    String surname = user.getSurname();
        //});


}
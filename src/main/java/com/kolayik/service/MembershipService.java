package com.kolayik.service;

import com.kolayik.dto.request.AddMembershipRequestDto;
import com.kolayik.dto.request.BuyMembershipRequestDto;
import com.kolayik.entity.Company;
import com.kolayik.entity.Membership;
import com.kolayik.entity.User;
import com.kolayik.repository.CompanyRepository;
import com.kolayik.repository.MembershipRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.utility.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public void addMembership(AddMembershipRequestDto dto) {
        Membership membership = Membership.builder()
                .name(dto.name())
                .price(dto.price())
                .createdAt(LocalDateTime.now())
                .plan(dto.plan())
                .description(dto.description())
                .userLimit(dto.userLimit())
                .build();
        membershipRepository.save(membership);
    }

    public List<Membership> getAllMembership() {
        return membershipRepository.findAll();
    }

    public void buyMembership(Long membershipId,Long userId) {
        // Kullanıcıyı getir
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Şirket kontrolü
        if (user.getCompanyId() == null) {
            throw new IllegalStateException("User is not associated with any company.");
        }

        Company company = companyRepository.findById(user.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + user.getCompanyId()));

        if (company.getStatus() != Status.AKTIF) {
            throw new IllegalStateException("User's company is not active. Cannot assign membership.");
        }

        // Kullanıcının mevcut üyeliği varsa kontrol et
        Optional<Membership> existingMembershipOpt = membershipRepository.findByUserId(userId);
        if (existingMembershipOpt.isPresent()) {
            Membership existingMembership = existingMembershipOpt.get();

            // Kullanıcı aynı üyelik paketini almaya çalışıyorsa hata ver
            if (existingMembership.getId().equals(membershipId)) {
                throw new IllegalStateException("You have already purchased this membership.");
            }

            // Farklı paketse eskiyi sil
            membershipRepository.delete(existingMembership);
        }

        // Yeni üyeliği getir
        Membership newMembership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("Membership not found with id: " + membershipId));

        // Yeni üyeliği kullanıcıya ata
        newMembership.setUserId(userId);
        membershipRepository.save(newMembership);
    }


    public void editMembership( AddMembershipRequestDto dto,Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("Membership not found with ID: " + membershipId));

        membership.setName(dto.name());
        membership.setPlan(dto.plan());
        membership.setDescription(dto.description());
        membership.setPrice(dto.price());
        membership.setUserLimit(dto.userLimit());
        membership.setCreatedAt(LocalDateTime.now());
        membershipRepository.save(membership);





    }

    public void deleteMembership(Long membershipId) {
        membershipRepository.deleteById(membershipId);
    }
}

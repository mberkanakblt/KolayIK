package com.kolayik.service;

import com.kolayik.dto.request.AddMembershipRequestDto;
import com.kolayik.dto.request.BuyMembershipRequestDto;
import com.kolayik.entity.Membership;
import com.kolayik.repository.MembershipRepository;
import com.kolayik.repository.UserRepository;
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
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Membership membership = membershipRepository.findById(membershipId).get();
        membership.setUserId(userId);
        membershipRepository.save(membership);
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

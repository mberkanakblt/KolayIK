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


}

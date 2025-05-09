package com.kolayik.service;

import com.kolayik.entity.UserRole;
import com.kolayik.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public List<UserRole> findAllRole(Long userId){
        return userRoleRepository.findByUserId(userId);
    }


    public void addRole(String roleName, Long userId){
        userRoleRepository.save(UserRole.builder()
                .roleName(roleName)
                .userId(userId)
                .build());
    }


}

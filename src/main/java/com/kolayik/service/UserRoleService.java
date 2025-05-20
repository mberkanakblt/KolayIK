package com.kolayik.service;

import com.kolayik.entity.User;
import com.kolayik.entity.UserRole;
import com.kolayik.repository.UserRepository;
import com.kolayik.repository.UserRoleRepository;
import com.kolayik.utility.enums.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public List<UserRole> findAllRole(Long personnelId){
        User user = userRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("Personnel not found with id: " + personnelId));
        return userRoleRepository.findByPersonnelId(user.getId());
    }

    public void addRole(Role roleName, Long personnelId){
        User user = userRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("Personnel not found with id: " + personnelId));
        UserRole userRole = UserRole.builder()
                .roleName(roleName)
                .personnel(user)
                .build();
        userRoleRepository.save(userRole);
    }
}





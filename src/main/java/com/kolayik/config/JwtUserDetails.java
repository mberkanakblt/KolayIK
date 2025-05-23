package com.kolayik.config;

import com.kolayik.entity.UserRole;
import com.kolayik.service.UserRoleService;
import com.kolayik.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetailsService {

    private final UserService userService;
    private final UserRoleService userRoleService;

    /**
     *Şeyma ekledi sorulacak?
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.kolayik.entity.User> user = userService.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<UserRole> roles = userRoleService.findAllRole(user.get().getId());
        roles.forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name()));
        });

        return User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .authorities(grantedAuthorities)
                .build();
    }

    public UserDetails loadUserById(Long userId) {
        Optional<com.kolayik.entity.User> user = userService.findByUserId(userId);
        if (user.isEmpty()) {
            return null;
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<UserRole> roleLists = userRoleService.findAllRole(userId);
        roleLists.forEach(r -> {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + r.getRoleName().name()));
        });

        return User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .authorities(grantedAuthorities)
                .build();
    }
}

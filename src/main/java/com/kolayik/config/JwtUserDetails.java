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
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserById(Long userId) {
        // 1. adım bu id ye sahip bir kullanıcı var mı?
        Optional<com.kolayik.entity.User> user = userService.findByUserId(userId);
        if(user.isEmpty()) return null;
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // user role servisinden userId si,ne ait rollerin listesini çelkiyoruz.
        List<UserRole> roleLists = userRoleService.findAllRole(userId);
        // bu rol listesini GrantedAuthority içerisine ekliyoruz.
        roleLists.forEach(r->{
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+r.getRoleName()));
        });

        return User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .accountLocked(false)
                .accountExpired(false)
                .authorities(grantedAuthorities)
                .build();
    }
}

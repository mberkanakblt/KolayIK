package com.kolayik.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class KolayIkSecurityConfig {

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * Belli URL adreslerine, kullanıcılar tarafından girilmesine izin verilerek
         * belli adreslerin erişimi kapatılacak.
         * Erişimi kapatılan adreslere kontrollü erişim ve rol tabanlı erişim sağlanacak.
         */
        http.authorizeHttpRequests(req -> {
            req
                    .requestMatchers("v3/api-docs/**","/dev/v1/user/**",
                            "/dev/v1/comment/**",
                            "/swagger-ui/**",
                            "/dev/v1/user/**","/dev/v1/membership/**"
                    ,"/dev/v1/company/**",
                            "/dev/v1/allow/**",
                            "/dev/v1/allowmanage/**" // belirli bir URL adresine erişimi yönet

                    ) // belirli bir URL adresine erişimi yönet
                    .permitAll() // yukarıdaki adrese ve adreslere izin ver.
                    /**
                     * Aşağıdakiler rollere göre izin verme şuanda roller kapalidir.!!
                     */
      //                 .requestMatchers("/dev/v1/comment/add-comment").hasAuthority("MANAGER")
                    // Yukarıdaki, Oturum açanın yetki kimliği USER, ADMİN (VS)... tipindeyse erişime izin ver
                    .anyRequest() //yapılan tüm istek türleri(/admin ,/user,comment/getById...)
                    .authenticated(); // oturum açma zorunluluğu getirir.

        });
        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(c -> corsConfigurationSource());
        /**
         * Kullanıcıların sisteme nasıl giriş yapılacakları. Yani kendi kimliklerini nasıl doğrulayacaklar.
         *
         */
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

}

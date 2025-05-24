package com.kolayik.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;



import java.security.Key;

import java.util.*;

import static javax.crypto.Cipher.SECRET_KEY;

/**
 * ----- Token Sınıfı.
 */
@Service
public class JwtManager {

    private String secretKey= "${my-jwt.secret-key}";
    private String issuer = "Admin1";
    private Long expirationDate = 1000L * 60*60*5;
    public String createToken(Long userId){
        String token="";
        Long now = System.currentTimeMillis(); // şuan ki zamanını long olarak değerini verir
        Date issuerAt = new Date(now);
        Date expiration = new Date(now + expirationDate);
        Algorithm algorithm = Algorithm.HMAC512(secretKey); // şifreleme algoritması.
        token = JWT.create()
                .withAudience()
                .withIssuer(issuer)
                .withIssuedAt(issuerAt)
                .withExpiresAt(expiration)
                .withClaim("userId", userId)
                .withClaim("KolayIK", "Yeni bir uygulama yazdık")
                .withClaim("log", "şuan saat tam olara "+ (new Date()))
                .sign(algorithm);
        return token;
    }


    public Optional<Long> validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier =  JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token); // aslında hem token bize mi ait hemde süresi doldu mu?
            if(Objects.isNull(decodedJWT)) // eğer decotedjwt boş ise
                return Optional.empty();
            Long userId = decodedJWT.getClaim("userId").asLong(); // ilgili claim nesnesini long olarak al
            return Optional.of(userId); // değeri optional olarak döndür.
        }catch (Exception exception){
            return Optional.empty();
        }
    }

    public Long getIdFromToken(String token) {
        try {
            // "Bearer " varsa temizle
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // Token 3 parçaya ayrılır: header.payload.signature
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Geçersiz JWT token");
            }

            // Payload base64 decode edilir
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

            // JSON olarak parse edilir
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> payloadMap = mapper.readValue(payloadJson, Map.class);

            // "id" alanı alınır
            Object id = payloadMap.get("userId");
            if (id == null) {
                throw new IllegalArgumentException("Token içinde 'id' alanı yok");
            }

            return Long.parseLong(id.toString());

        } catch (Exception e) {
            throw new RuntimeException("Token çözümlenemedi: " + e.getMessage());
        }
    }


}

package com.meetnow.meetnowbackend.domain.jwt.service;


import com.meetnow.meetnowbackend.domain.jwt.constant.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private String accessTokenExpirationTime = "6000000000"; // 약 60일쯤

    @Value("${jwt.secret}")
    private String tokenSecret;

    public String createToken(String username){
        Date accessTokenExpireTime = createAccessTokenExpireTime();

        return createAccessToken(username, accessTokenExpireTime);
    }

    private String createAccessToken(String username, Date expirationTime) {
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name())    // 토큰 제목 ACCESS
                .setAudience(username)                 //  토큰에 담을 개인정보(대상자)
                .setIssuedAt(new Date())        //  발급시간
                .setExpiration(expirationTime)  //
                .signWith(SignatureAlgorithm.HS512, tokenSecret)    // 시그니처를 만들 알고리즘과 시크릿키 값
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    private Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }


    public String getUsername(String accessToken) {
        String name ="";
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(accessToken).getBody();
            name = claims.getAudience();
        } catch (Exception e){
            e.printStackTrace();

        }
        return name;
    }

    public boolean validateToken(String token) {
        try {
            // access Token 을 검증만 하는 것이기 때문에, getBody()로 Claim 을 가져와 무언가를 하진 않는다.
            Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e){   //  토큰 변조
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}


























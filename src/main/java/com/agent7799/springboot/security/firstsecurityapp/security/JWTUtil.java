package com.agent7799.springboot.security.firstsecurityapp.security;

import com.agent7799.springboot.security.firstsecurityapp.services.PersonDetailsService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret") // from .properties file
    private String secretKey;

    @Value("${app_name") // from .properties file
    private String appName;

    public String generateToken(String username){

        return JWT.create()
                .withSubject("userDetails")
                .withClaim("username", username)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException  {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                .withSubject("userDetails")
                .withIssuer(appName)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }


}

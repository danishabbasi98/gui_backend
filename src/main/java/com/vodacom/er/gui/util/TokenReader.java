package com.vodacom.er.gui.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;

public class TokenReader {
    public UUID extractUUIDKeyFromToken(String token) {
        String SECRET_KEY = "mySecretKey";
        String encodedToken = token.substring("Bearer ".length());
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(encodedToken);

        String uuid = claims.getBody().get("uuid", String.class);
        return UUID.fromString(uuid);
    }
    public String extractUserNameFromToken(String token) {
        String SECRET_KEY = "mySecretKey";
        String encodedToken = token.substring("Bearer ".length());
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(encodedToken);

        String userName = claims.getBody().get("sub", String.class);
        return userName;
    }
}

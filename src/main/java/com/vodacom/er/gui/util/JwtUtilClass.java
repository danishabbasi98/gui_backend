package com.vodacom.er.gui.util;

import com.vodacom.er.gui.service.EsSessionService;
import com.vodacom.er.gui.service.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import java.util.function.Function;

//@Service("permissionValidator")
@Service
public class JwtUtilClass {

    private final String SECRET_KEY = "mySecretKey";

    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    private EsSessionService esSessionService;

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token , MyUserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            if(username.equals(userDetails.getUsername()) && !isTokenExpired(token)){
                Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        String role = myUserDetails.getRole(username);
        if(role == null){
            return null;
        }
        else{
            UUID randomUUID = UUID.randomUUID();
            String uuidString = randomUUID.toString();
            claims.put("role",role);
            claims.put("uuid",uuidString);
            esSessionService.createSessionByUserSeq(myUserDetails.getUser(username),uuidString);
            return createToken(claims, username);
        }
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
//

}

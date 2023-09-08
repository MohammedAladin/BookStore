package com.Integration.NTI.Security;

import com.Integration.NTI.Exception.CustomException;
import com.Integration.NTI.Models.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    final String CLAIMS_SUBJECT="sub";
    final String CLAIMS_CREATED="created";


    private final String TOKEN_SECRET = "SecretToken";

    public String generateToken(User user){

        Map<String, Object> Claims = new HashMap<>();
        Claims.put(CLAIMS_SUBJECT, user.getUserName());

        return Jwts.builder()
                .setSubject(user.getUserName())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .compact();

    }
    public String getUserNameFromToken(String token) throws CustomException {

        Claims claims = getClaims(token);

        if(claims == null){
            throw new CustomException("This User Is Not Authorized..", HttpStatus.NOT_FOUND);
        }
        return claims.getSubject();

    }
    public Date generateExpirationDate(){
        long TOKEN_VALIDITY = 604800L;
        return new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000);
    }
    private boolean isTokenExpired(String token){
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails) throws CustomException {
        String userName = getUserNameFromToken(token);
        if(userName == null)
            throw new CustomException("Token Does not Has username",HttpStatus.FORBIDDEN);

        return (userDetails.getUsername().equals(userName) && !isTokenExpired(token));
    }

    public Claims getClaims(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

}

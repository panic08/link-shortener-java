package ru.panic.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtDecoder {
    @Value("${spring.security.jwt.secret}")
    private String secret;

    public Claims decodeJwt(String jwt) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
    }

    public <T> T getClaimFromJwt(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = decodeJwt(jwt);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromJwt(String jwt) {
        return getClaimFromJwt(jwt, Claims::getSubject);
    }

    public Date getExpirationDateFromJwt(String jwt) {
        return getClaimFromJwt(jwt, Claims::getExpiration);
    }

    public Boolean isJwtExpired(String jwt) {
        return getExpirationDateFromJwt(jwt).before(new Date());
    }
    public Boolean isJwtValid(String jwt) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException ex) {
            // JWT был некорректно сформирован
        } catch (ExpiredJwtException ex) {
            // JWT истек
        } catch (UnsupportedJwtException ex) {
            // JWT имеет неподдерживаемый формат
        } catch (IllegalArgumentException ex) {
            // JWT пустой или null
        }
        return false;
    }
}
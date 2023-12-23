package test.springbootsecurity.springbootWithSecurity.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import test.springbootsecurity.springbootWithSecurity.constantutilities.ConstantUtil;
import test.springbootsecurity.springbootWithSecurity.model.User;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtil implements Serializable {
    private static final long serialVersionUID = 1234567L;

    @Value("${JWT.secret}")
    private String secret_key;

   @Bean
    public JwtParser getInstance()
    {
        return Jwts.parser().setSigningKey(secret_key).build();
    }

    public String createToken(User user)
   {
       Claims claims = Jwts.claims().setSubject(user.getEmail()).build();
       Date tokenTimeCreated=new Date();
       Date tokenValidity=new Date(tokenTimeCreated.getTime()+ TimeUnit.MINUTES.toMinutes(ConstantUtil.ACCESSTOKENVALIDITY));
       return Jwts.builder()
               .setClaims(claims)
               .setExpiration(tokenValidity)
               .signWith(SignatureAlgorithm.HS256, secret_key)
               .compact();
   }
    private Claims parseJwtClaims(String token) throws Exception {
      return (Claims) getInstance().parse(token).getPayload();
    }
    public Claims resolveClaims(HttpServletRequest req) throws Exception {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }
    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(ConstantUtil.TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(ConstantUtil.TOKEN_PREFIX)) {
            return bearerToken.trim().substring(ConstantUtil.TOKEN_PREFIX.length()).trim();
        }
        return null;
    }
    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }

}

package com.nvp.domaci3.utils;

import com.nvp.domaci3.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "ipW4xilYFBgHJ0FGdl9f1EpnUGo+DY5mDUE46l1CEB6VsBgPm9PqEHHHQOIpo+A1Z2OsorozWFBtG40bz582sA==";

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();

      claims.put("id", user.getId());
      claims.put("can_create_users", user.getCan_create_users());
      claims.put("can_read_users", user.getCan_read_users());
      claims.put("can_update_users", user.getCan_update_users());
      claims.put("can_delete_users", user.getCan_delete_users());
      claims.put("can_search_order", user.getCan_search_order());
      claims.put("can_place_order", user.getCan_place_order());
      claims.put("can_cancel_order", user.getCan_cancel_order());
      claims.put("can_track_order", user.getCan_track_order());
      claims.put("can_schedule_order", user.getCan_schedule_order());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public boolean validateToken(String token, UserDetails user) {
        return (user.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }
}

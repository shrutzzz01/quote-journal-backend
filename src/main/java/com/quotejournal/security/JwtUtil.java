package com.quotejournal.security;

import com.quotejournal.entity.User;
import com.quotejournal.repository.UserRepository;
import com.quotejournal.service.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final UserRepository userRepository;
    public JwtUtil(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    private final Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long EXPIRATION=1000*60*60;
    public String generateToken(User user){
        String role = user.getRole().toString();// Returns "ADMIN"
        return Jwts.builder()
                .signWith(key)
                .claim("role", role)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .compact();
    }
    public String extractUsername(String token){
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
    public Date extractExpirationDate(String token){
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
    }
    public boolean validateToken(String token, UserDetails userDetails){
        String email=extractUsername(token);
        Date expiration=extractExpirationDate(token);
        boolean isExpired= expiration.before(new Date());
        return(email.equals(userDetails.getUsername()) && !isExpired);
    }

    public String extractRole(String token) {
        String email=extractUsername(token);
        User user= userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return user.getRole().toString();
    }
}

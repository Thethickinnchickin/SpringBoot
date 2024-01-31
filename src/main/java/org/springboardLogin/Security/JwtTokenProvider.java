package org.springboardLogin.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Component class responsible for JWT token generation, validation, and extraction.
 */
@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Autowired
    private UserDetailsService userDetailsService;

    private Key jwtSecretKey;

    // Constructor to initialize the JwtTokenProvider with the provided JWT secret
    public JwtTokenProvider(@Value("${app.jwtSecret}") String jwtSecret) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generates a JWT token for the given UserDetails.
     *
     * @param userDetails UserDetails object for which the token is generated.
     * @return JWT token as a string.
     */
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token JWT token as a string.
     * @return Username extracted from the token.
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException ex) {
            // Token has expired
            // You can log this event or perform any necessary actions
            return null;
        }
    }

    /**
     * Validates the JWT token.
     *
     * @param authToken JWT token as a string.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            // Log signature exception
        } catch (MalformedJwtException ex) {
            // Log malformed JWT exception
        } catch (ExpiredJwtException ex) {
            // Log expired JWT exception
        } catch (UnsupportedJwtException ex) {
            // Log unsupported JWT exception
        } catch (IllegalArgumentException ex) {
            // Log illegal argument exception
        }
        return false;
    }

    /**
     * Gets the Authentication object for the user based on the JWT token.
     *
     * @param token JWT token as a string.
     * @return Authentication object.
     */
    public Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Extracts the JWT token from the request headers.
     *
     * @param request HttpServletRequest object.
     * @return JWT token as a string or null if not found.
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

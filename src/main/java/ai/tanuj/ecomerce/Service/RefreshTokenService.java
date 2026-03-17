package ai.tanuj.ecomerce.Service;
import ai.tanuj.ecomerce.Entity.RefreshToken;
import ai.tanuj.ecomerce.Model.TokenRefreshRequest;
import ai.tanuj.ecomerce.Repository.RefreshTokenRepository; 
import ai.tanuj.ecomerce.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.Instant;
import java.util.UUID;
import java.util.Optional;
@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;
    // 1. CREATE: Generate a 7-day token for a user
    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findByEmail(email).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(604800000)); // 7 Days
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    // 2. VERIFY: Check if the token has expired
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    // Inside RefreshTokenService.java
public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
}
}
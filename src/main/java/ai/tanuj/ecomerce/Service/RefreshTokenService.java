package ai.tanuj.ecomerce.Service;
import ai.tanuj.ecomerce.Entity.RefreshToken;
import ai.tanuj.ecomerce.Entity.UserEntity;
import ai.tanuj.ecomerce.Repository.RefreshTokenRepository; 
import ai.tanuj.ecomerce.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
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
    @Transactional
public RefreshToken createRefreshToken(String email) {
    UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
            .orElse(new RefreshToken()); // If not found, create new object

    // 2. Update the fields (this works for both NEW and EXISTING tokens)
    refreshToken.setUser(user);
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setExpiryDate(Instant.now().plusMillis(604800000)); // 7 days

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
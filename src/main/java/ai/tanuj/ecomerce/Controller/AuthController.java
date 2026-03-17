//Checks your password once. If correct, it calls the Printer to give you a Token.
package ai.tanuj.ecomerce.Controller;

import ai.tanuj.ecomerce.Entity.RefreshToken;
import ai.tanuj.ecomerce.Entity.UserEntity;
import ai.tanuj.ecomerce.Model.LoginRequest;
import ai.tanuj.ecomerce.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ai.tanuj.ecomerce.Repository.UserRepository;
import ai.tanuj.ecomerce.Service.RefreshTokenService;
import ai.tanuj.ecomerce.Model.Role;
import ai.tanuj.ecomerce.Model.TokenRefreshRequest;
import ai.tanuj.ecomerce.Model.TokenResponse;

import org.springframework.security.crypto.password.PasswordEncoder;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    // 1. Authenticate the user
    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    // 2. Generate Access Token (JWT)
    String accessToken = jwtUtil.generateToken(request.getEmail(), "ADMIN");

    // 3. THE FIX: Generate AND Save the Refresh Token to MySQL
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());

    // 4. Return both as a JSON object
    return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken.getToken()));
}

    @PostMapping("/register")
    public String register(@RequestBody UserEntity user) {
        // 1. Hash the password (NEVER save raw passwords!)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 2. Set a default role if none provided
        if(user.getRole() == null) user.setRole(Role.CUSTOMER);
        
        userRepository.save(user);
        return "User registered successfully! Now go to /login to get your token.";
    }
    //refresh token logic 
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody TokenRefreshRequest request) {
    return refreshTokenService.findByToken(request.getRefreshToken())
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                // Logic: Generate a brand new Access Token (JWT)
                String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
                return ResponseEntity.ok(new TokenResponse(accessToken, request.getRefreshToken()));
            })
            .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
}
}

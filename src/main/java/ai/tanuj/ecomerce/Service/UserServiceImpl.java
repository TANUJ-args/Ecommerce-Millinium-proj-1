package ai.tanuj.ecomerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.tanuj.ecomerce.Entity.UserEntity;
import ai.tanuj.ecomerce.Model.User;
import ai.tanuj.ecomerce.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public String registerUser(User user) {
        UserEntity userEntity = new UserEntity();
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            return "User already exists";
        }
        if(user.getEmail()==null||user.getPassword()==null){
            return "Email or Password should no be empty";
        }
        userEntity.setEmail(user.getEmail().toLowerCase());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(user.getRole());
        userRepository.save(userEntity);
        return "User registered sucessfully";
    }

    @Override
    public String loginUser(User user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail().toLowerCase()).orElse(null);
        if(userEntity == null){
            return "User not found";
        }
        if(passwordEncoder.matches(user.getPassword(), userEntity.getPassword())){
            return "Login successful";
        }
        return "Invalid credentials";
    }
    
}

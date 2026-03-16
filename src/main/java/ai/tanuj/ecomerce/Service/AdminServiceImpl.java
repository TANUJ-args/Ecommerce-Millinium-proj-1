package ai.tanuj.ecomerce.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.tanuj.ecomerce.Entity.UserEntity;
import ai.tanuj.ecomerce.Model.User;
import ai.tanuj.ecomerce.Repository.UserRepository;

@Service

public class AdminServiceImpl implements AdminService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> ReadAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            users.add(user);
        }
        return users;
    }
    @Override
    public String UserSetRole(String email, String role) {
        UserEntity userEntity = userRepository.findByEmail(email.toLowerCase()).orElse(null);
        if(userEntity == null){
            return "User not found";
        }
        try {
            userEntity.setRole(ai.tanuj.ecomerce.Model.Role.valueOf(role.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return "Invalid role. Valid roles are: ADMIN, CUSTOMER, VENDOR";
        }
        userRepository.save(userEntity);
        return "User role updated successfully";
    }
    @Override
    public String UserDelete(String email) {
        UserEntity userEntity = userRepository.findByEmail(email.toLowerCase()).orElse(null);
        if(userEntity == null){
            return "User not found";
        }
        userRepository.delete(userEntity);
        return "User deleted successfully";
    }
    }


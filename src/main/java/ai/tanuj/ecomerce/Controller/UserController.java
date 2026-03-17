package ai.tanuj.ecomerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ai.tanuj.ecomerce.Model.User;
import ai.tanuj.ecomerce.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String getMethodName() {
        return "hello String";
    }
    
    @PostMapping("/auth/register")
    public String CreateUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
    
    
}

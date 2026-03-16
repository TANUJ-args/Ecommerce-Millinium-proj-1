package ai.tanuj.ecomerce.Controller;

import org.springframework.web.bind.annotation.RestController;

import ai.tanuj.ecomerce.Model.User;
import ai.tanuj.ecomerce.Service.AdminService;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@RequestMapping("/admin")
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<User> getallusers() {
        return adminService.ReadAllUsers();
    }
    
    @PostMapping("/setrole")
    public String postMethodName(@RequestBody Map<String, String> body) {
        return adminService.UserSetRole(body.get("email"), body.get("role"));
    }
    
    @PostMapping("/deleteuser")
    public String postMethodName(@RequestParam String email) {
        return adminService.UserDelete(email);
    }
    
}

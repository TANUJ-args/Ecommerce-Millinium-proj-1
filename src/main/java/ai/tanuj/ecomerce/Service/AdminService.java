package ai.tanuj.ecomerce.Service;

import java.util.List;

import ai.tanuj.ecomerce.Model.User;
public interface AdminService {
    List<User> ReadAllUsers();
    String UserSetRole(String email, String role);
    String UserDelete(String email);
}

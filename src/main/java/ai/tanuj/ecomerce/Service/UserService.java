package ai.tanuj.ecomerce.Service;

import ai.tanuj.ecomerce.Model.User;

public interface UserService {
    String registerUser(User user);
    String loginUser(User user);
}

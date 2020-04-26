package pl.grafikpka.service;

import pl.grafikpka.model.User;

import java.util.List;

public interface UserService {
    List<User>getAllUsers();

    boolean isUserPresent(String username);

    void createUser(User user);
}

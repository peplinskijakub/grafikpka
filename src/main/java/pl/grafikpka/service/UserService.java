package pl.grafikpka.service;

import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Profile("web")
public interface UserService {
    List<User> getAllUsers();

    boolean isUserPresent(String username);

    boolean createUser(User user);

    List<User> findAll();

    Optional<User> findById(String id);

    boolean createUsersFromFile(MultipartFile file) throws IOException;
}

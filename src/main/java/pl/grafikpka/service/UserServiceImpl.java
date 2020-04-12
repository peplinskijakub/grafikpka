package pl.grafikpka.service;

import org.springframework.stereotype.Service;
import pl.grafikpka.model.User;
import pl.grafikpka.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User>getAllUsers(){
        return userRepository.findAll();
    }
}

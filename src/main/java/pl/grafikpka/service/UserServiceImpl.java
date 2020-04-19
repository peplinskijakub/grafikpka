package pl.grafikpka.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.grafikpka.model.Role;
import pl.grafikpka.model.User;
import pl.grafikpka.repository.UserRepository;

import java.util.ArrayList;
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

    public List<User> findByName(String name) {
        return  userRepository.findByUsernameLike("%"+name+"%");
    }

    public void createUser(User user) {
        BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole = Role.USER;
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void createAdmin(User user) {
        BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole = Role.ADMIN;
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public User findOne(String user) {
        return userRepository.findByUsername(user);
    }

    public boolean isUserPresent(String s) {
        User u=userRepository.findByUsername(s);
        if(u!=null)
            return true;

        return false;
    }


}

package pl.grafikpka.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.grafikpka.config.MyPasswordGenerator;
import pl.grafikpka.model.Role;
import pl.grafikpka.model.User;
import pl.grafikpka.repository.UserRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MyPasswordGenerator myPasswordGenerator;

//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findByName(String name) {
        return userRepository.findByUsernameLike("%" + name + "%");
    }

    public boolean createUsersFromFile(MultipartFile file) throws IOException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(';')
                .withRecordSeparator(",").withIgnoreEmptyLines());
        List<User> users = new ArrayList<>();
        for (CSVRecord record : csvParser) {
            User user = new User();
            user.setUsername(record.get(0).trim());
            user.setPassword(encoder.encode(myPasswordGenerator.generateStrongPassword()));
            Role userRole = Role.USER;
            List<Role> roles = new ArrayList<>();
            roles.add(userRole);
            user.setRoles(roles);
            user.setActive(true);
            users.add(user);

        }
        userRepository.insert(users);
        return false;
    }

    public boolean createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = Role.USER;
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setActive(true);
        userRepository.save(user);
        return false;
    }

    public void createAdmin(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = Role.ADMIN;
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setActive(true);
        userRepository.save(user);
    }

    public void createModerator(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole = Role.MODERATOR;
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setActive(true);
        userRepository.save(user);
    }

    public User findOne(String user) {
        return userRepository.findByUsername(user);
    }

    public boolean isUserPresent(String s) {
        User u = userRepository.findByUsername(s);
        if (u != null)
            return true;

        return false;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}

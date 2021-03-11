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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MyPasswordGenerator myPasswordGenerator;

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
        //FileWriter out = new FileWriter("kontaKierowcow.csv");
       // CSVPrinter printer = new CSVPrinter(out, CSVFormat.EXCEL);/// zle powinno odczytac plik wygenerowc hasla i zapisać plik na dysku i do db
        List<User> users = new ArrayList<>();
        String name = "";
        String pass = "";
        for (CSVRecord record : csvParser) {
            name = record.get(0).trim();
            pass = myPasswordGenerator.generateStrongPassword();
            User user = new User();
            user.setUsername(record.get(0).trim());
            user.setPassword(encoder.encode(pass));
            user.setTempPassword(pass);
            user.setRoles(Arrays.asList(Role.USER));
            user.setActive(true);
            users.add(user);
        }
        reader.close();
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

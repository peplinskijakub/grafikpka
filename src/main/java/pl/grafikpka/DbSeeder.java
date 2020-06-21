package pl.grafikpka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.grafikpka.repository.UserRepository;
@Service
public class DbSeeder implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public DbSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.userRepository.deleteAll();
//
//        User user = new User("user",passwordEncoder.encode("user123"),true, Collections.singletonList(Role.USER),"");
//        User admin = new User("admin", passwordEncoder.encode("admin123"),true, Collections.singletonList(Role.ADMIN), "ACCESS_TEST1,ACCESS_TEST2");
//        User manager = new User("manager", passwordEncoder.encode("manager123"),true, Collections.singletonList(Role.MANAGER), "ACCESS_TEST1");
//
//        List<User> users = Arrays.asList(user, admin, manager);
//        this.userRepository.saveAll(users);
    }
}

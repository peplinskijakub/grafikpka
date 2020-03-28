package pl.grafikpka.sec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.grafikpka.model.User;
import pl.grafikpka.repository.UserRepository;
@Service
public class UserPrincipalDetailService implements UserDetailsService {
    private UserRepository userRepository;

    public UserPrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //find User
        User user = this.userRepository.findByUsername(s);
        //trzeba przekonwertować usera na user principal
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}

package pl.grafikpka.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grafikpka.model.User;
import pl.grafikpka.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldGetAllUsers() {
        //given
        when(userRepository.findAll()).thenReturn(Arrays.asList(
           new User("test1","test1",true),
           new User("test2","test2",true),
           new User("test3","test3",true)
        ));
        //when
        List<User>allUsers = userService.getAllUsers();
        //then
        assertThat(allUsers).hasSize(3);

    }
}
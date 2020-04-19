package pl.grafikpka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.grafikpka.model.User;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByUsername(String username);

    List<User> findByUsernameLike(String s);
}

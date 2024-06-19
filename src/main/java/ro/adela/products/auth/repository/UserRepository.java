package ro.adela.products.auth.repository;

import org.springframework.stereotype.Repository;
import ro.adela.products.auth.model.User;

@Repository
public class UserRepository {
    public User findUserByEmail(String email){
        User user = new User(email,"123456");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        return user;
    }
}
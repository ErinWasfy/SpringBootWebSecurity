package test.springbootsecurity.springbootWithSecurity.userrepository;

import org.springframework.stereotype.Repository;
import test.springbootsecurity.springbootWithSecurity.model.User;

@Repository
public class UserRepository {

    public User findUserByEmail(String email)
    {
        User user=new User("nick","erin.saad@gmail.com");
        user.setPassword("12345");
        return user;
    }
}

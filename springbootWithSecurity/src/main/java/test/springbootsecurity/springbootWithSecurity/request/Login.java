package test.springbootsecurity.springbootWithSecurity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    private String username;
    private String password;
    private String email;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

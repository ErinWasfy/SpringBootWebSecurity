package test.springbootsecurity.springbootWithSecurity.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String userName;
    private String password;
    private String email;

    @JsonCreator
    public User(@JsonProperty("userName")String userName,@JsonProperty("email") String email) {
        this.userName = userName;
        this.email = email;
    }
}

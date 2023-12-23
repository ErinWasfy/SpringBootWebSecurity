package test.springbootsecurity.springbootWithSecurity.userrestcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class UserController {

    @GetMapping("/home")
    public ResponseEntity<String> redirectToHomePage()
    {
         return new ResponseEntity<String>("Welcome to Home page", HttpStatus.OK);
    }
}

package test.springbootsecurity.springbootWithSecurity.userrestcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import test.springbootsecurity.springbootWithSecurity.authentication.JWTUtil;
import test.springbootsecurity.springbootWithSecurity.model.User;
import test.springbootsecurity.springbootWithSecurity.request.Login;
import test.springbootsecurity.springbootWithSecurity.response.ErrorResponse;
import test.springbootsecurity.springbootWithSecurity.response.UserInfo;

@RestController
@RequestMapping("/rest/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil=jwtUtil;
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Login login)
    {
        try
        {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            String name = authentication.getName();
            User user = new User(name,login.getEmail());
            String token = jwtUtil.createToken(user);
            UserInfo userInfo=new UserInfo(login.getUsername(),login.getEmail(),token);
            return ResponseEntity.ok(userInfo);
        }
        catch (BadCredentialsException e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}

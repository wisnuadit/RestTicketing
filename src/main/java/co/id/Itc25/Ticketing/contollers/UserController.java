package co.id.Itc25.Ticketing.contollers;

import co.id.Itc25.Ticketing.dtos.user.UserUsernamePasswordDto;
import co.id.Itc25.Ticketing.dtos.user.UsernamePasswrodDto;
import co.id.Itc25.Ticketing.services.MyUserDetailsService;
import co.id.Itc25.Ticketing.utilities.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private MyUserDetailsService myUserDetailsService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public UserController(MyUserDetailsService myUserDetailsService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.myUserDetailsService = myUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public Authentication index(Authentication authentication){
        return authentication;
    }

    @PostMapping("jwt")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody UsernamePasswrodDto credentials){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()
        ));

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(credentials.getUsername());

        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }
}

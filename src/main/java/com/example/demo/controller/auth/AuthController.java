package com.example.demo.controller.auth;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repos.UserRepo;
import com.example.demo.services.admin.AdminService;
import com.example.demo.services.auth.AuthService;
import com.example.demo.services.jwt.UserService;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser (@RequestBody SignUpRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE). body ("User already exist with this email");
        UserDto createdUserDto = authService.signUpUser(signupRequest);
        if (createdUserDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST). body ("User not created");
        return ResponseEntity.status(HttpStatus.CREATED). body (createdUserDto);
    }

    @PostMapping("/log")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        //validating the email and password matching
        //below is not working
/*        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }*/
        //alternative way to demonstrate
        if(!authenticationRequest.getEmail().split("@")[0].equals(authenticationRequest.getPassword())){
            throw new BadCredentialsException("Incorrect username or password");
        }

        //Issuing the token------------------------------------------------------------------------->>>>
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepo.findByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);

        //making the token details into a response object
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()){
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setId(optionalUser.get().getId());
            authenticationResponse.setRole(optionalUser.get().getRole());
        }
        return authenticationResponse;
    }

}

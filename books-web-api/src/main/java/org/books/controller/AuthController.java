package org.books.controller;


import org.books.builder.UserBuilder;
import org.books.enums.RoleType;
import org.books.model.User;
import org.books.payload.request.UserSignInPayload;
import org.books.payload.request.UserSignUpPayload;
import org.books.payload.response.JWTResponse;
import org.books.repository.RoleRepository;
import org.books.repository.UserRepository;
import org.books.security.model.UserDetailsData;
import org.books.security.utils.TokenUtil;
import org.books.security.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSignInPayload signInData) {

        Authentication authentication = Utility.authenticate(authenticationManager, signInData);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String userToken = TokenUtil.create(authentication);
        UserDetailsData userDetails = (UserDetailsData) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JWTResponse(userToken,
                userDetails.getActualUser().getId(),
                userDetails.getUsername(),
                userDetails.getActualUser().getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSignUpPayload signUpData) {

        if (userRepository.existsByUsername(signUpData.getUsername())) {
            return ResponseEntity.badRequest().body("Error: This username already exists.");
        }
        if (userRepository.existsByEmail(signUpData.getEmail())) {
            return ResponseEntity.badRequest().body("Error: This email already exists.");
        }

        User user = UserBuilder.create()
                .withUsername(signUpData.getUsername())
                .withPassword(passwordEncoder.encode(signUpData.getPassword()))
                .withRoles(roleRepository, RoleType.ROLE_ADMINISTRATOR)
                .withContact(signUpData.getContact())
                .withEmail(signUpData.getEmail())
                .withCpf(signUpData.getCpf())
                .get();

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully.");
    }

}

package com.prajwal.campushub.auth;

import com.prajwal.campushub.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.prajwal.campushub.repository.StudentRepository;
import com.prajwal.campushub.entity.Student;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/login")
    public LoginResponseDTO login(
            @RequestBody LoginRequestDTO request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),

                        request.getPassword()

                )

        );

        Student student =
                studentRepository.findByEmail(request.getEmail())
                        .orElseThrow();

        String token =
                jwtService.generateToken(request.getEmail());

        return new LoginResponseDTO(
                token,
                student.getRole()
        );

    }

}
package com.prajwal.campushub.security;

import com.prajwal.campushub.entity.Student;
import com.prajwal.campushub.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Student student = repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Student Not Found"));

        return new User(
                student.getEmail(),
                student.getPassword(),
                List.of(new SimpleGrantedAuthority(student.getRole()))
        );
    }
}
package com.prajwal.campushub.repository;

import com.prajwal.campushub.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student,Long> {

    Optional<Student> findByEmail(String email);

    List<Student> findByFirstNameContainingIgnoreCase(String firstName);
}
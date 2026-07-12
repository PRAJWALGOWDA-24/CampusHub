package com.prajwal.campushub;

import com.prajwal.campushub.dto.StudentRequestDTO;
import com.prajwal.campushub.dto.StudentResponseDTO;
import com.prajwal.campushub.entity.Student;
import com.prajwal.campushub.repository.StudentRepository;
import com.prajwal.campushub.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService service;

    @Test
    void testSaveStudent() {

        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setRegistrationNumber("OXF001");
        dto.setFirstName("Prajwal");
        dto.setLastName("Gowda");
        dto.setEmail("prajwal@gmail.com");
        dto.setPassword("12345");
        dto.setRole("ROLE_STUDENT");

        when(passwordEncoder.encode("12345"))
                .thenReturn("encodedPassword");

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setRegistrationNumber("OXF001");
        savedStudent.setFirstName("Prajwal");
        savedStudent.setLastName("Gowda");
        savedStudent.setEmail("prajwal@gmail.com");

        when(repository.save(any(Student.class)))
                .thenReturn(savedStudent);

        StudentResponseDTO response =
                service.saveStudent(dto);

        assertEquals("Prajwal", response.getFirstName());
        assertEquals("OXF001",
                response.getRegistrationNumber());

        verify(passwordEncoder, times(1))
                .encode("12345");

        verify(repository, times(1))
                .save(any(Student.class));
    }

    @Test
    void testGetStudentById() {

        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Prajwal");

        when(repository.findById(1L))
                .thenReturn(Optional.of(student));

        Student result =
                service.getStudentById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Prajwal",
                result.getFirstName());

        verify(repository)
                .findById(1L);
    }

    @Test
    void testDeleteStudent() {

        Student student = new Student();
        student.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(student));

        String result =
                service.deleteStudent(1L);

        assertEquals(
                "Student Deleted Successfully",
                result);

        verify(repository)
                .delete(student);
    }
}
package com.prajwal.campushub.service;

import com.prajwal.campushub.dto.StudentRequestDTO;
import com.prajwal.campushub.dto.StudentResponseDTO;
import com.prajwal.campushub.entity.Student;
import com.prajwal.campushub.exception.StudentNotFoundException;
import com.prajwal.campushub.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    //  SAVE
    public StudentResponseDTO saveStudent(StudentRequestDTO dto) {

        Student student = new Student();

        student.setRegistrationNumber(dto.getRegistrationNumber());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGender(dto.getGender());

        // NEW
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setRole(dto.getRole());

        Student savedStudent = repository.save(student);

        StudentResponseDTO response = new StudentResponseDTO();

        response.setId(savedStudent.getId());
        response.setRegistrationNumber(savedStudent.getRegistrationNumber());
        response.setFirstName(savedStudent.getFirstName());
        response.setLastName(savedStudent.getLastName());
        response.setEmail(savedStudent.getEmail());
        response.setPhoneNumber(savedStudent.getPhoneNumber());
        response.setDateOfBirth(savedStudent.getDateOfBirth());
        response.setGender(savedStudent.getGender());
        response.setRole(savedStudent.getRole());

        return response;
    }
    // GET ALL
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    //  GET BY ID
    public Student getStudentById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID : " + id));
    }

    //UPDATE
    public Student updateStudent(Long id, Student student) {

        Student existingStudent = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID : " + id));

        existingStudent.setRegistrationNumber(student.getRegistrationNumber());
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPhoneNumber(student.getPhoneNumber());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        existingStudent.setGender(student.getGender());

        return repository.save(existingStudent);
    }

    //PATCH
    public Student patchStudent(Long id, Student student) {

        Student existingStudent = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID : " + id));

        if (student.getRegistrationNumber() != null)
            existingStudent.setRegistrationNumber(student.getRegistrationNumber());

        if (student.getFirstName() != null)
            existingStudent.setFirstName(student.getFirstName());

        if (student.getLastName() != null)
            existingStudent.setLastName(student.getLastName());

        if (student.getEmail() != null)
            existingStudent.setEmail(student.getEmail());

        if (student.getPhoneNumber() != null)
            existingStudent.setPhoneNumber(student.getPhoneNumber());

        if (student.getDateOfBirth() != null)
            existingStudent.setDateOfBirth(student.getDateOfBirth());

        if (student.getGender() != null)
            existingStudent.setGender(student.getGender());
        if(student.getRole() != null)
            existingStudent.setRole(student.getRole());

        return repository.save(existingStudent);
    }

    //DELETE
    public String deleteStudent(Long id) {

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID : " + id));

        repository.delete(student);

        return "Student Deleted Successfully";
    }

    //pagiantion  + sorting (both asce and desc )
    public Page<Student> getStudentsWithPagination(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(pageable);
    }
//search API (andre searh byfirstnme or pra retunr prajwal (all no case sensitive
    public List<Student> searchStudentsByFirstName(String firstName) {

        return repository.findByFirstNameContainingIgnoreCase(firstName);

    }
 //for reset password
    public String resetPassword(Long id, String password){

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found"));

        student.setPassword(
                passwordEncoder.encode(password));

        repository.save(student);

        return "Password Reset Successfully";

    }
}
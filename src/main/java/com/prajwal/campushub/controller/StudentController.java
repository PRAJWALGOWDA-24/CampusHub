package com.prajwal.campushub.controller;

import com.prajwal.campushub.dto.ResetPasswordDTO;
import com.prajwal.campushub.entity.Student;
import com.prajwal.campushub.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import com.prajwal.campushub.dto.StudentRequestDTO;
import com.prajwal.campushub.dto.StudentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/students")
@Tag(
        name = "Student Management",
        description = "CRUD APIs for managing students in CampusHub"
)
public class StudentController {

    @Autowired   //controll needs service
    private StudentService service;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")

    @Operation(
            summary = "Create Student",
            description = "Creates a new student in the CampusHub system"
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })

    public StudentResponseDTO saveStudent(
            @Valid @RequestBody StudentRequestDTO dto) {

        return service.saveStudent(dto);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get All Students",
            description = "Returns the complete list of students"
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Students fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public List<Student> getAllStudents() {

        return service.getAllStudents();

    }

   @GetMapping("/{id}")
   @PreAuthorize("isAuthenticated()")
   @Operation(
           summary = "Get Student By ID",
           description = "Returns a single student using the student ID"
   )

   @ApiResponses({
           @ApiResponse(responseCode = "200", description = "Student found"),
           @ApiResponse(responseCode = "404", description = "Student not found")
   })
    public Student getStudentById(@Parameter(description = "Student ID", example = "1")
                                     @PathVariable Long id) {
        return service.getStudentById(id);
    }
    /*@GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {

        System.out.println("Controller received id = " + id);

        return service.getStudentById(id);
    }

*/

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Student updateStudent(@PathVariable Long id,
                                 @RequestBody Student student){

        return service.updateStudent(id, student);

    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Student patchStudent(@PathVariable Long id,
                                @RequestBody Student student) {

        return service.patchStudent(id, student);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteStudent(@PathVariable Long id) {
        return service.deleteStudent(id);
    }

    //pagination + mapping
    @GetMapping("/page")
    @PreAuthorize("isAuthenticated()")
    public Page<Student> getStudentsWithPagination(

            @RequestParam(defaultValue = "0") int page,  //y 0 and 5 it is deafult if user value not get give as back just deafult

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return service.getStudentsWithPagination(
                page,
                size,
                sortBy,
                direction);

    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public List<Student> searchStudents(

            @RequestParam String firstName) {

        return service.searchStudentsByFirstName(firstName);

    }
  //resetting passwor feature
    @PutMapping("/{id}/reset-password")
    @PreAuthorize("hasRole('ADMIN')")
    public String resetPassword(
            @PathVariable Long id,
            @RequestBody ResetPasswordDTO dto){

        return service.resetPassword(id, dto.getPassword());

    }
}

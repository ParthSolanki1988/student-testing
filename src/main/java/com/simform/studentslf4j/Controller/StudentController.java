package com.simform.studentslf4j.Controller;

import com.simform.studentslf4j.Entity.Student;
import com.simform.studentslf4j.Service.StudentService;
import com.simform.studentslf4j.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/students")
public class StudentController {

  @Autowired
  private StudentService studentService;

  // build create User REST API

  //  CREATE
  @PostMapping
  public ResponseEntity<Student> createStudents(@RequestBody Student student){
      log.info("Create User");
      Student saveStudent = studentService.createStudent(student);
      return new ResponseEntity<>(saveStudent , HttpStatus.CREATED);
  }

  //READ

  // build get user by id REST API
  // http://localhost:8080/api/v1/students
  @GetMapping
  public ResponseEntity<List<Student>> getAllStudents(){
    log.info("Getting all student ");
    List<Student> listOfStudent = studentService.getAllStudents();
    return new ResponseEntity<>(listOfStudent , HttpStatus.FOUND);
  }


  @GetMapping("{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id){
    log.info("Getting Student By Id");
    Student student = studentService.findById(id);
    System.out.println(student);
    if (student != null){
      return new ResponseEntity<>(student , HttpStatus.FOUND );
    }else {
      throw new NotFoundException("Student Not Found");
    }

  }


  //UPDATE

  // http://localhost:8080/api/v1/students/{id}
  @PutMapping("{id}")
    public ResponseEntity<Student> updatedStudent(@PathVariable("id") Long id,@RequestBody Student student){
    boolean isDigit = true;

   if (id == 0 || id < 0){
     log.warn("Enter Valid Id for update student");
     return null;
   }
   else {
     log.info("updating the student , Id : " + id );
     student.setId(id);
     Student updateStudents = studentService.updateStudent(student);
     return new ResponseEntity<>(updateStudents , HttpStatus.OK);
   }
  }


  // DELETE

  // http://localhost:8080/api/v1/students/{id}
  @DeleteMapping("{id}")
  public ResponseEntity<String> deleteStudent(@PathVariable long id){
    if (id == 0 || id < 0){
      log.warn("Enter Valid id for delete student");
      return null;
    }
    else{
      log.info("deleting the Student, Id:" + id);
      studentService.deleteStudent(id);
      return new ResponseEntity<>("Delete Student Successfully!" , HttpStatus.OK);
    }

  }


}

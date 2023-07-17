package com.simform.studentslf4j.Service.Impl;

import com.simform.studentslf4j.Entity.Student;
import com.simform.studentslf4j.Repository.StudentRepository;
import com.simform.studentslf4j.Service.StudentService;
import com.simform.studentslf4j.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class StudentServiceImplementation implements StudentService {

  @Autowired
  StudentRepository studentRepository;

  @Override
  public Student createStudent(Student student) {
    if (student != null){
      return studentRepository.save(student);
    }
    else {
      throw new NotFoundException("Student not found");
    }

  }

  @Override
  public List<Student> getAllStudents() {

    if(studentRepository.count() == 0){
      log.error("Student Not Found");
    }
    else {
      log.info("Student list is Available");
    }


    List<Student> all = studentRepository.findAll();

    return all;


  }



  @Override
  public Student updateStudent(Student student) {
    Optional<Student> optionalStudent = studentRepository.findById(student.getId());
    Student existingStudent = optionalStudent.get();
    if (optionalStudent.isPresent()){
      if (student.getId() == 0 ){
        student.setId(existingStudent.getId());

      }
      if (student.getName() == "" || student.getName() == null){
        student.setName(existingStudent.getName());

      }
      if (student.getTechnology() == "" || student.getTechnology() == null){
        student.setTechnology(existingStudent.getTechnology());

      }
      existingStudent.setId(student.getId());
      existingStudent.setName(student.getName());
      existingStudent.setTechnology(student.getTechnology());

      return studentRepository.save(existingStudent);
    }
    else {
      throw new NotFoundException("Student Not Found");
    }


  }

  @Override
  public Student deleteStudent(long id) {
    Optional<Student> optionalStudent = studentRepository.findById(id);
    if (optionalStudent.isEmpty()){
      return null;
    }
    else {
      studentRepository.deleteById(id);
      return optionalStudent.get();

    }

  }

  @Override
  public Student findById(Long id) {
    return studentRepository.findById(id).get();
  }
}

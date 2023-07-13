package com.simform.studentslf4j.Service;

import com.simform.studentslf4j.Entity.Student;
import java.util.List;


public interface StudentService {

  Student createStudent(Student student);

  List<Student> getAllStudents();

  Student updateStudent(Student student);

  Student deleteStudent(long id);


  Student findById(Long id);
}

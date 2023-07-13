package com.simform.studentslf4j.Service;

import com.simform.studentslf4j.Entity.Student;
import com.simform.studentslf4j.Repository.StudentRepository;
import com.simform.studentslf4j.Service.Impl.StudentServiceImplementation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@DisplayName("Test : Service level")
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  StudentRepository studentRepository;


  @InjectMocks
  StudentServiceImplementation studentServiceImplementation;

  Student student;

  Student student1;

  Student student3;

  List<Student> studentList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    student = Student.builder()
            .name("MihirRaj")
            .technology("dotNet")
            .build();

    student1 = Student.builder()
            .id(1290L)
            .name("ParthRaj")
            .technology("Java")
            .build();

    studentList.add(student);
    studentList.add(student1);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void givenStudentObject_whenSaveStudent_thenReturnStudentObject_positive() {
    //given
    given(studentRepository.save(student)).willReturn(student);

    //when
    Student save = studentServiceImplementation.createStudent(student);

    //assert
    assertThat(save.getId()).isEqualTo(student.getId());
  }

  @Test
  void givenStudentObject_whenSaveStudent_thenReturnStudentObject_negative() {
    //given
    given(studentRepository.save(student3)).willReturn(student3);

    //when
    Student save = studentServiceImplementation.createStudent(student3);

    //assert
    assertThat(save).isNull();
  }


  @Test
  void getAllStudents() {
    when(studentRepository.findAll()).thenReturn(studentList);

    List<Student> allStudents = studentServiceImplementation.getAllStudents();
    System.out.println(allStudents);

    assertThat(allStudents).isNotNull();
  }

  @Test
  void updateStudent() {
    when(studentRepository.save(student)).thenReturn(student);
    when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));


    student.setName("Vijay");
    student.setTechnology("React");

    Student updateStudent = studentServiceImplementation.updateStudent(student);

    System.out.println(updateStudent);

    assertThat(updateStudent.getName()).isEqualTo("Vijay");
    assertThat(updateStudent.getTechnology()).isEqualTo("React");
  }

  @Test
  void deleteStudent() {
    given(studentRepository.findById(student.getId())).willReturn(Optional.of(student));

    Student deleteStudent = studentServiceImplementation.deleteStudent(student.getId());
    System.out.println(deleteStudent);

    assertThat(deleteStudent).isNotNull();

  }

  @Test
  void findById() {
    when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

    Student findedStudent = studentServiceImplementation.findById(student.getId());
    System.out.println(findedStudent);

    assertThat(findedStudent).isNotNull();
  }
}
package com.simform.studentslf4j.Repository;

import com.simform.studentslf4j.Entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test : Repository level")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

  @Autowired
  StudentRepository studentRepository;

  Student student;

  Student savedStudent;
  Student student2;


  @BeforeEach
  void setUp() {
    student = Student.builder()
            .id(1010L)
            .name("MihirRaj")
            .technology("Stack")
            .build();
     savedStudent = studentRepository.save(student);
  }

  @Test
  @DisplayName("Test : save()")
  void saveTestSuccess(){
    student2 = Student.builder()
            .id(1012L)
            .name("Arpit")
            .technology("KOTA")
            .build();
    savedStudent = studentRepository.save(student2);
    Student actualStudent = studentRepository.findById(student2.getId()).get();
    assertThat(actualStudent).isEqualTo(savedStudent);
  }

  @Test
  @DisplayName("Test : FindAll()")
  void findAllTest() {
    List<Student> studentList = studentRepository.findAll();
    assertThat(studentList.size()).isEqualTo(13);
  }

  @Test
  @DisplayName("Test : FindById()")
  void findByIdTest() {
    student2 = studentRepository.findById(1010L).get();
    assertThat(student2.getId()).isEqualTo(student.getId());

  }

  @Test
  @DisplayName("Test : DeleteById()")
  public void deleteById(){
    studentRepository.deleteById(1010L);
    Optional<Student> optionalStudent = studentRepository.findById(student.getId());
    assertThat(optionalStudent.isPresent()).isFalse();
  }

  @AfterEach
  void tearDown() {
    student = null;
    studentRepository =null;
  }

}
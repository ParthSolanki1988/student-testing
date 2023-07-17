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

  Student nullStudent;


  @BeforeEach
  void setUp() {
    student = Student.builder()
            .id(1010L)
            .name("MihirRaj")
            .technology("Stack")
            .build();
  }

  @Test
  @DisplayName("Test : save()")
  void saveTestSuccess(){
    //Act
    Student saveStudent = studentRepository.save(student);

    //Asserts
    assertThat(saveStudent).isNotNull();
  }

  @Test
  @DisplayName("Test : FindAll()")
  void findAllTest() {
    List<Student> all = studentRepository.findAll();
    assertThat(all).isNotNull();
  }

  @Test
  @DisplayName("Test : FindById()")
  void findByIdTest() {
    Optional<Student> optionalStudent = studentRepository.findById(student.getId());
    assertThat(optionalStudent).isNotNull();
  }

  @Test
  @DisplayName("Test : DeleteById()")
  public void deleteById(){
    studentRepository.deleteById(student.getId());
    Optional<Student> optionalStudent = studentRepository.findById(student.getId());
    assertThat(optionalStudent).isEmpty();
  }

  @AfterEach
  void tearDown() {
    student = null;
    studentRepository =null;
  }

}
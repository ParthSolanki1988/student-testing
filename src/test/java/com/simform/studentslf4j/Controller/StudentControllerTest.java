package com.simform.studentslf4j.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simform.studentslf4j.Entity.Student;
import com.simform.studentslf4j.Service.Impl.StudentServiceImplementation;
import com.simform.studentslf4j.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

  Student student;
  Student student1;
  Student student3;
  List<Student> studentList = new ArrayList<>();
  List<Student> studentArrayList = new ArrayList<>();
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  StudentServiceImplementation studentServiceImplementation;
  @Autowired
  StudentController studentController;

  @BeforeEach
  void setUp() {
    student = Student.builder().id(1010L).name("MihirRaj").technology("dotNet").build();

    student1 = Student.builder().id(1290L).name("ParthRaj").technology("Java").build();

    studentList.add(student);
    studentList.add(student1);
  }

  /*save student test cases*/
  @Nested
  class saveStudent{
    @Test
    void givenStudentObject_whenSaveStudent_thenReturnStudentObject_positive() throws Exception {
      ObjectMapper objectMapper = new ObjectMapper();
      String requestJson = objectMapper.writeValueAsString(student);

      when(studentServiceImplementation.createStudent(student)).thenReturn(student);

      MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(requestJson))
              .andDo(print())
              .andExpect(status().isCreated()).andReturn();

      String contentAsString = result.getResponse().getContentAsString();
      assertThat(contentAsString).isEqualTo(requestJson);
    }

    @Test
    void givenStudentObject_whenSaveStudent_thenReturnStudentObject_negative() throws Exception {
      ObjectMapper objectMapper = new ObjectMapper();
      String requestJson = objectMapper.writeValueAsString(student);

      when(studentServiceImplementation.createStudent(student)).thenThrow(new NotFoundException("Student cannot be null"));

      mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestJson))
              .andDo(print())
              .andExpect(status().isNotFound());
    }
  }
  /*End save student test cases*/


  /*Get All student test cases*/
 @Nested
 class GetAllStudent{
    @Test
    void givenStudentList_whenGetAllStudent_returnStudentList_positive() throws Exception {
      when(studentServiceImplementation.getAllStudents()).thenReturn(studentList);
      mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students"))
              .andDo(print())
              .andExpect(status().isFound());
    }

    @Test
    void givenStudentList_whenGetAllStudent_returnStudentList_negative() throws Exception {
      when(studentServiceImplementation.getAllStudents()).thenReturn(studentArrayList);
      mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students"))
              .andDo(print())
              .andExpect(status().isNotFound());
    }
  }
  /*End Get All student test cases*/


  /* Get student By Id Test cases*/

 @Nested
 class GetStudentById{
   @Test
   void givenStudentId_whenGetStudentById_thenReturnStudentObject_positive() throws Exception {

     //given
     when(studentServiceImplementation.findById(1010L)).thenReturn(student);

     mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/1010"))
             .andDo(print())
             .andExpect(status().isFound());
   }

   @Test
   void givenStudentId_whenGetStudentById_thenReturnStudentObject_negative() throws Exception {
     when(studentServiceImplementation.findById(101L)).thenThrow(new NotFoundException("Student Not Found"));
     mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/101"))
             .andDo(print())
             .andExpect(status().isNotFound());
   }
 }
  /* End Get student By Id Test cases*/

  @Nested
  class updateStudent{
    @Test
    public void givenStudentId_whenGetStudentById_thenReturnUpdateStudentObject_positive() throws Exception{
      ObjectMapper objectMapper = new ObjectMapper();

      Student updatedStudent = Student.builder()
              .name("MihirRaj")
                .technology("MEAN").build();

      String requestJson = objectMapper.writeValueAsString(updatedStudent);

      when(studentServiceImplementation.updateStudent(updatedStudent)).thenReturn(updatedStudent);
      ResponseEntity<Student> studentResponseEntity = studentController.updatedStudent(1010L, updatedStudent);
      mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/students/1010")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(requestJson))
                      .andExpect(status().isOk()).andDo(print()).andReturn();

    }

    @Test
    public void givenStudentId_whenGetStudentById_thenReturnUpdateStudentObject_negative() throws Exception{
      Student updatedStudent = Student.builder()
              .id(101L)
              .name("MihirRaj")
              .technology("MEAN").build();
      ObjectMapper objectMapper = new ObjectMapper();
      String requestJson = objectMapper.writeValueAsString(updatedStudent);

      when(studentServiceImplementation.updateStudent(updatedStudent)).thenThrow(new NotFoundException("Student not found"));
      mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/students/101")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestJson))
              .andDo(print())
              .andExpect(status().isNotFound());
    }
  }

  @Nested
  class DeleteStudent{
    @Test
    public void deleteStudent_positive() throws Exception {
      when(studentServiceImplementation.deleteStudent(1010)).thenReturn(student);
      mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/students/1010"))
              .andDo(print())
              .andExpect(status().isOk());
    }

    @Test
    public void deleteStudent_negative() throws Exception {
      when(studentServiceImplementation.deleteStudent(101)).thenThrow(new NotFoundException("Student Not Found"));
      mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/students/101"))
              .andDo(print())
              .andExpect(status().isNotFound());
    }
  }


  @AfterEach
  void tearDown() {
    student = null;
    student1 = null;
    student3 = null;
    studentList = null;
  }
}
